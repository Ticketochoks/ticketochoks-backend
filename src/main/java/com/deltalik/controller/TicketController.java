package com.deltalik.controller;

import com.deltalik.dto.ticket.TicketRequestDto;
import com.deltalik.dto.ticket.TicketResponseDto;
import com.deltalik.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/purchase")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TicketResponseDto> buyTicket(@Valid @RequestBody TicketRequestDto ticketRequestDto) {
        TicketResponseDto ticket = ticketService.purchase(ticketRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ticket);
    }
}
