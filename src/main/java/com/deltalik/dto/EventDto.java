package com.deltalik.dto;

import com.deltalik.entity.Location;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class EventDto {

  @Data
  public static class Request {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Location is required")
    @Valid
    private LocationDto location;

    @NotNull(message = "Start time is required")
    private ZonedDateTime startDateTime;

    @NotNull(message = "End time is required")
    private ZonedDateTime endDateTime;

    @Min(value = 1, message = "Number of available tickets must be at least 1")
    private int availableTickets;

    @Min(value = 0, message = "Ticket price must be non-negative")
    private int ticketPrice;
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class Response extends BaseResponse {

    private Long id;
    private String name;
    private LocationDto location;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private int availableTickets;
    private int ticketPrice;
  }
}
