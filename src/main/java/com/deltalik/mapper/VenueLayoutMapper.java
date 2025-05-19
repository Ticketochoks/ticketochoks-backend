package com.deltalik.mapper;

import com.deltalik.dto.venueLayout.VenueLayoutResponseDto;
import com.deltalik.entity.VenueLayout;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VenueLayoutMapper {
    VenueLayoutResponseDto toVenueLayoutResponseDto(VenueLayout layout);
}
