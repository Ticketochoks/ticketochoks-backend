package com.deltalik.dto.venueLayout;

import lombok.Data;

import java.util.List;

@Data
public class VenueLayoutResponseDto {

    private Long id;
    private String name;
    private List<SectionResponseDto> sections;
}
