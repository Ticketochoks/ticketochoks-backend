package com.deltalik.service;

import com.deltalik.dto.event.EventLocationDto;
import com.deltalik.dto.event.EventRequestDto;
import com.deltalik.dto.event.EventResponseDto;
import com.deltalik.entity.Event;
import com.deltalik.exception.ExceptionFactory;
import com.deltalik.mapper.EventMapper;
import com.deltalik.repository.EventRepository;
import jakarta.transaction.Transactional;
import java.time.ZonedDateTime;
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

  private final EventRepository eventRepository;
  private final EventMapper eventMapper;

  public EventResponseDto getEventById(Long id) {
    Event event = eventRepository.findById(id).orElseThrow(
        () -> ExceptionFactory.userNotFoundById(id)
    );

    log.info("Event is found by id {}", id);
    return eventMapper.toEventResponseDto(event);
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
    validateEventDoesNotExist(eventDto.getLocation(), eventDto.getStartDateTime(),
        eventDto.getEndDateTime());

    Event event = Event.builder()
        .name(eventDto.getName())
        .location(eventMapper.toLocation(eventDto.getLocation()))
        .startDateTime(eventDto.getStartDateTime())
        .endDateTime(eventDto.getEndDateTime())
        .availableTickets(eventDto.getAvailableTickets())
        .ticketPrice(eventDto.getTicketPrice())
        .build();

    Event savedEvent = eventRepository.save(event);
    log.info("Event is created with id {} and name {}", savedEvent.getId(), savedEvent.getName());
    return eventMapper.toEventResponseDto(savedEvent);
  }

  @Transactional
  public void deleteEventById(Long id) {
    eventRepository.findById(id).orElseThrow(
        () -> ExceptionFactory.eventNotFoundById(id)
    );

    log.info("Event with id {} id deleted", id);
    eventRepository.deleteById(id);
  }

  private void validateEventDoesNotExist(EventLocationDto eventLocationDto, ZonedDateTime startDateTime,
      ZonedDateTime endDateTime) {
    eventRepository.findByLocationAndTimeOverlap(eventMapper.toLocation(eventLocationDto),
            startDateTime, endDateTime)
        .ifPresent(
            event -> {
              throw ExceptionFactory.eventAlreadyExists(eventLocationDto.getStreet(),
                  eventLocationDto.getCity(),
                  startDateTime,
                  endDateTime);
            }
        );
  }
}
