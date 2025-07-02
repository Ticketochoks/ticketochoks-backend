package com.deltalik.controller;

import com.deltalik.dto.event.EventRequestDto;
import com.deltalik.dto.event.EventResponseDto;
import com.deltalik.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

  @Operation(summary = "Create a new event", description = "Create a new event with specified details")
  @SecurityRequirement(name = "bearerAuth")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Event successfully created",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = EventResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "405", description = "Method Not Allowed",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "409", description = "Event conflict - another event exists at the same time and location",
          content = @Content(mediaType = "application/json")),
  })
  @PostMapping
  public ResponseEntity<EventResponseDto> createEvent(
      @Valid @RequestBody EventRequestDto eventDto) {
    EventResponseDto createdEvent = eventService.createEvent(eventDto);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .header("Location", "/api/v1/events/" + createdEvent.getId())
        .body(createdEvent);
  }

  @Operation(summary = "Get event by ID", description = "Retrieve event details by ID")
  @SecurityRequirement(name = "bearerAuth")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Event successfully retrieved",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = EventResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid ID format",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "Event not found",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "405", description = "Method Not Allowed",
          content = @Content(mediaType = "application/json"))
  })
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

  @Operation(summary = "Delete event by ID", description = "Delete an event by its ID")
  @SecurityRequirement(name = "bearerAuth")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Event successfully deleted"),
      @ApiResponse(responseCode = "400", description = "Invalid ID format",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "Event not found",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "405", description = "Method Not Allowed",
          content = @Content(mediaType = "application/json"))
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEvent(
      @PathVariable @NotNull(message = "Id is required") Long id) {
    eventService.deleteEventById(id);
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .build();
  }
}
