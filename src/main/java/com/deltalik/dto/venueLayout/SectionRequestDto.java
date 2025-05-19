package com.deltalik.dto.venueLayout;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SectionRequestDto {

    @NotBlank(message = "Section name is required")
    private String name;

    @NotNull(message = "The number of rows per section is required")
    @Positive(message = "The number of rows per section must be positive")
    private Integer rows;

    @NotNull(message = "The number of seats per row is required")
    @Positive(message = "The number of seats per row must be positive")
    private Integer seatsPerRow;
}
