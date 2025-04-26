package com.deltalik.mapper;

import com.deltalik.dto.event.EventLocationRequestDto;
import com.deltalik.dto.event.EventLocationResponseDto;
import com.deltalik.dto.event.EventResponseDto;
import com.deltalik.entity.Event;
import com.deltalik.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

  EventResponseDto toEventResponseDto(Event event);
  EventLocationResponseDto toLocationResponseDto(Location location);
  Location toLocation(EventLocationRequestDto eventLocationRequestDto);
}
