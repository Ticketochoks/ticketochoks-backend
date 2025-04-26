package com.deltalik.dto.event;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EventLocationRequestDto {

  @NotBlank(message = "Street is required")
  private String street;

  @NotBlank(message = "City is required")
  private String city;

  @NotBlank(message = "Country is required")
  private String country;
}
