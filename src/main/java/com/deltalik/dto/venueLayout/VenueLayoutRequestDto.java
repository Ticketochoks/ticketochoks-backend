package com.deltalik.dto.venueLayout;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class VenueLayoutRequestDto {

    @NotBlank(message = "Venue layout name is required")
    private String name;

    @NotEmpty(message = "At least one section is required")
    private List<SectionRequestDto> sections;
}
