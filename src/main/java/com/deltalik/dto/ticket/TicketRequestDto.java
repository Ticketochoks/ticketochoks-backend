package com.deltalik.dto.ticket;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketRequestDto {

    @NotNull
    private Long eventId;

    @Valid
    private SeatDto seatDto;
    // TODO: add generalAdmission -> no seat/row
}

