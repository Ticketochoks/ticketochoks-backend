package com.deltalik.mapper;

import com.deltalik.dto.event.EventLocationDto;
import com.deltalik.dto.event.EventResponseDto;
import com.deltalik.entity.Event;
import com.deltalik.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

  EventResponseDto toEventResponseDto(Event event);
  EventLocationDto toLocationDto(Location location);
  Location toLocation(EventLocationDto eventLocationDto);
}
