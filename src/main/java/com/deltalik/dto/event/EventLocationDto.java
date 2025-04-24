package com.deltalik.dto.event;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EventLocationDto {

  @NotBlank(message = "Street is required")
  private String street;

  @NotBlank(message = "City is required")
  private String city;

  @NotBlank(message = "Country is required")
  private String country;

  @NotBlank(message = "Timezone is required")
  private String timezone;
}
