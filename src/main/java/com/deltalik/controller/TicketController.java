package com.deltalik.controller;

import com.deltalik.dto.ticket.TicketRequestDto;
import com.deltalik.dto.ticket.TicketResponseDto;
import com.deltalik.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

  @Operation(summary = "Purchase ticket", description = "Buy a ticket for an event")
  @SecurityRequirement(name = "bearerAuth")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Ticket successfully purchased",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = TicketResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "405", description = "Method Not Allowed",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "409", description = "Ticket already sold",
          content = @Content(mediaType = "application/json"))
  })
  @PostMapping("/purchase")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<TicketResponseDto> buyTicket(
      @Valid @RequestBody TicketRequestDto ticketRequestDto) {
    TicketResponseDto ticket = ticketService.purchase(ticketRequestDto);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ticket);
  }
}
