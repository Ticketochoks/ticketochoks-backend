package com.deltalik.service;

import com.deltalik.dto.event.EventLocationRequestDto;
import com.deltalik.dto.event.EventRequestDto;
import com.deltalik.dto.event.EventResponseDto;
import com.deltalik.entity.Event;
import com.deltalik.entity.Location;
import com.deltalik.exception.ExceptionFactory;
import com.deltalik.mapper.EventMapper;
import com.deltalik.repository.EventRepository;
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
        .availableTickets(event.getAvailableTickets())
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

    Event event = Event.builder()
        .name(eventDto.getName())
        .location(eventMapper.toLocation(eventDto.getLocation()))
        .startDateTime(startDateTime)
        .endDateTime(endDateTime)
        .timezone(timezone)
        .availableTickets(eventDto.getAvailableTickets())
        .ticketPrice(eventDto.getTicketPrice())
        .build();

    Event savedEvent = eventRepository.save(event);
    log.info("Event is created with id {} and name {}", savedEvent.getId(), savedEvent.getName());

    return EventResponseDto.builder()
        .id(event.getId())
        .name(event.getName())
        .location(eventMapper.toLocationResponseDto(event.getLocation()))
        .startDateTime(startDateTime)
        .endDateTime(endDateTime)
        .timezone(event.getTimezone())
        .availableTickets(event.getAvailableTickets())
        .ticketPrice(event.getTicketPrice())
        .build();

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
