package com.deltalik.service;

import com.deltalik.dto.event.EventLocationRequestDto;
import com.deltalik.dto.event.EventRequestDto;
import com.deltalik.dto.event.EventResponseDto;
import com.deltalik.entity.*;
import com.deltalik.exception.ExceptionFactory;
import com.deltalik.mapper.EventMapper;
import com.deltalik.repository.EventRepository;
import com.deltalik.repository.SeatRepository;
import com.deltalik.repository.VenueLayoutRepository;
import jakarta.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class EventService {

  private final DateTimeService dateTimeService;
  private final EventRepository eventRepository;
  private final EventMapper eventMapper;
  private final SeatRepository seatRepository;
  private final VenueLayoutRepository venueLayoutRepository;

  public EventResponseDto getEventById(Long id) {
    Event event = eventRepository.findById(id).orElseThrow(
        () -> ExceptionFactory.eventNotFoundById(id)
    );

    log.info("Event is found by id {}", id);

    return EventResponseDto.builder()
        .id(event.getId())
        .name(event.getName())
        .location(eventMapper.toLocationResponseDto(event.getLocation()))
        .startDateTime(event.getStartDateTime())
        .endDateTime(event.getEndDateTime())
        .timezone(event.getTimezone())
        .ticketPrice(event.getTicketPrice())
        .build();
  }

  public Page<EventResponseDto> getAllEvents(Pageable pageable) {
    if (pageable.getPageSize() > 10) {
      pageable = PageRequest.of(
          pageable.getPageNumber(),
          100,
          pageable.getSort()
      );
      log.warn("Page size limited to 10 records");
    }

    return eventRepository.findAll(pageable).map(eventMapper::toEventResponseDto);
  }

  @Transactional
  public EventResponseDto createEvent(EventRequestDto eventDto) {
    ZonedDateTime startDateTime, endDateTime;

    String format = eventDto.getDateTimeFormat();
    String timezone = eventDto.getTimezone();

    startDateTime = dateTimeService.parse(
        eventDto.getStartDateTime(),
        Optional.ofNullable(format),
        Optional.ofNullable(timezone)
    );

    endDateTime = dateTimeService.parse(
        eventDto.getEndDateTime(),
        Optional.ofNullable(format),
        Optional.ofNullable(timezone)
    );

    validateEventDoesNotExist(eventDto.getLocation(), startDateTime, endDateTime);

    VenueLayout layout = venueLayoutRepository.findByName(eventDto.getVenueLayoutType().getLabel())
            .orElseThrow(() -> ExceptionFactory.venueLayoutNotFoundByName(eventDto.getVenueLayoutType().getLabel()));

    Event event = Event.builder()
        .name(eventDto.getName())
        .location(eventMapper.toLocation(eventDto.getLocation()))
        .startDateTime(startDateTime)
        .endDateTime(endDateTime)
        .timezone(timezone)
        .ticketPrice(eventDto.getTicketPrice())
        .venueLayout(layout)
        .build();

    Event savedEvent = eventRepository.save(event);
    log.info("Event is created with id {} and name {}", savedEvent.getId(), savedEvent.getName());

    generateSeatsForLayout(savedEvent, layout);

    return EventResponseDto.builder()
        .id(event.getId())
        .name(event.getName())
        .location(eventMapper.toLocationResponseDto(event.getLocation()))
        .startDateTime(startDateTime)
        .endDateTime(endDateTime)
        .timezone(event.getTimezone())
        .ticketPrice(event.getTicketPrice())
        .build();

  }

  private void generateSeatsForLayout(Event event, VenueLayout layout) {
    for (int rowNum = 1; rowNum <= layout.getRows(); rowNum++) {
      for (int seatNum = 1; seatNum <= layout.getSeatsPerRow(); seatNum++) {
        Seat seat = Seat.builder()
                .event(event)
                .rowNumber(rowNum)
                .seatNumber(seatNum)
                .status(SeatStatus.AVAILABLE)
                .build();

        seatRepository.save(seat);
      }
    }
  }

  @Transactional
  public void deleteEventById(Long id) {
    eventRepository.findById(id).orElseThrow(
        () -> ExceptionFactory.eventNotFoundById(id)
    );

    log.info("Event with id {} id deleted", id);
    eventRepository.deleteById(id);
  }

  private void validateEventDoesNotExist(EventLocationRequestDto eventLocationRequestDto,
      ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
    Location location = eventMapper.toLocation(eventLocationRequestDto);
    eventRepository.findByLocationAndTimeOverlap(location, startDateTime, endDateTime)
        .ifPresent(
            event -> {
              throw ExceptionFactory.eventAlreadyExists(eventLocationRequestDto.getStreet(),
                  eventLocationRequestDto.getCity(),
                  startDateTime,
                  endDateTime);
            }
        );
  }
}
