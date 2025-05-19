package com.deltalik.mapper;

import com.deltalik.dto.ticket.SeatDto;
import com.deltalik.entity.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SeatMapper {
    SeatDto toSeatDto(Seat seat);
}
