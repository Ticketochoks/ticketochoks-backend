package com.deltalik.mapper;

import com.deltalik.dto.EventDto;
import com.deltalik.dto.LocationDto;
import com.deltalik.entity.Event;
import com.deltalik.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

  EventDto.Response toEventResponseDto(Event event);
  LocationDto toLocationDto(Location location);
  Location toLocation(LocationDto locationDto);
}
