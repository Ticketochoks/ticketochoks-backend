package com.deltalik.mapper;

import com.deltalik.dto.ticket.TicketResponseDto;
import com.deltalik.entity.Ticket;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = SeatMapper.class)
public interface TicketMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "seat.event.id", target = "eventId")
    @Mapping(source = "seat", target = "seatDto")
    TicketResponseDto toTicketResponseDto(Ticket ticket);
}
