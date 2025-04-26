package com.deltalik.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class EventRequestDto {

  @NotBlank(message = "Name is required")
  private String name;

  @NotNull(message = "Location is required")
  @Valid
  private EventLocationRequestDto location;

  @NotBlank(message = "Start date/time is required")
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private String startDateTime;

  @NotBlank(message = "End date/time is required")
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private String endDateTime;

  @NotBlank(message = "Timezone is required")
  private String timezone;

  @NotNull(message = "Available tickets is required")
  @Positive(message = "Available tickets must be positive")
  private Integer availableTickets;

  @NotNull(message = "Ticket price is required")
  @Positive(message = "Ticket price must be positive")
  private Integer ticketPrice;

  private String dateTimeFormat;
}

