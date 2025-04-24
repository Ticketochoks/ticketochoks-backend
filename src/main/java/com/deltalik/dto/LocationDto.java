package com.deltalik.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LocationDto {

  @NotBlank(message = "Street is required")
  private String street;

  @NotBlank(message = "City is required")
  private String city;

  @NotBlank(message = "Country is required")
  private String country;

  @NotBlank(message = "Timezone is required")
  private String timezone;
}
