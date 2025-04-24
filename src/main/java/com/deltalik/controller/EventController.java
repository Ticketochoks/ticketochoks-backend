package com.deltalik.controller;

import com.deltalik.dto.event.EventRequestDto;
import com.deltalik.dto.event.EventResponseDto;
import com.deltalik.service.EventService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("api/v1/events")
public class EventController {

  private final EventService eventService;

  @PostMapping
  public ResponseEntity<EventResponseDto> createEvent(@Valid @RequestBody EventRequestDto eventDto) {
    EventResponseDto createdEvent = eventService.createEvent(eventDto);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .header("Location", "/api/v1/events/" + createdEvent.getId())
        .body(createdEvent);
  }

  @GetMapping("/{id}")
  public ResponseEntity<EventResponseDto> getEvent(
      @PathVariable @NotNull(message = "Id is required") Long id) {
    EventResponseDto event = eventService.getEventById(id);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(event);
  }

  @GetMapping
  public Page<EventResponseDto> getEvents(Pageable pageable) {
    return eventService.getAllEvents(pageable);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEvent(
      @PathVariable @NotNull(message = "Id is required") Long id) {
    eventService.deleteEventById(id);
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .build();
  }
}
