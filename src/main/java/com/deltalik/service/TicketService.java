package com.deltalik.service;

import com.deltalik.dto.ticket.TicketRequestDto;
import com.deltalik.dto.ticket.TicketResponseDto;
import com.deltalik.entity.*;
import com.deltalik.exception.ExceptionFactory;
import com.deltalik.mapper.TicketMapper;
import com.deltalik.repository.EventRepository;
import com.deltalik.repository.SeatRepository;
import com.deltalik.repository.TicketRepository;
import com.deltalik.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;
    private final AuthenticationFacade authFacade;
    private final TicketMapper ticketMapper;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public TicketResponseDto purchase(TicketRequestDto ticketRequestDto) {
        User user = authFacade.getCurrentUser();

        Event event = eventRepository.findById(ticketRequestDto.getEventId())
                .orElseThrow(() -> ExceptionFactory.eventNotFoundById(ticketRequestDto.getEventId()));

        // add locking
        Seat seat = seatRepository.findByEventIdAndSeatNumberAndRowNumber(
                        ticketRequestDto.getEventId(),
                        ticketRequestDto.getSeatDto().getSeatNumber(),
                        ticketRequestDto.getSeatDto().getRowNumber())
                .orElseThrow(
                        () -> ExceptionFactory.seatNotFoundByEventIdAndRowAndSeat(
                                ticketRequestDto.getEventId(),
                                ticketRequestDto.getSeatDto().getRowNumber(),
                                ticketRequestDto.getSeatDto().getSeatNumber())
                );

        if (seat.getStatus() != SeatStatus.AVAILABLE) {
            throw ExceptionFactory.seatAlreadyTaken(
                    ticketRequestDto.getEventId(),
                    ticketRequestDto.getSeatDto().getRowNumber(),
                    ticketRequestDto.getSeatDto().getSeatNumber());
        }

        // check if user has sufficient funds
        seat.setStatus(SeatStatus.RESERVED);
        seatRepository.save(seat);

        try {
            // simulate payment
            Ticket ticket = Ticket.builder()
                    .user(user)
                    .status(TicketStatus.PURCHASED)
                    .purchaseDateTime(ZonedDateTime.now())
                    .seat(seat)
                    .build();

            Ticket savedTicket = ticketRepository.save(ticket);

            seat.setStatus(SeatStatus.PURCHASED);
            seatRepository.save(seat);

            log.info("Ticket purchased successfully: eventId={}, userId={}, seatNumber={}, rowNumber={}",
                    event.getId(), user.getId(), seat.getSeatNumber(), seat.getRowNumber());

            return ticketMapper.toTicketResponseDto(savedTicket);
        } catch (Exception e) {
            seat.setStatus(SeatStatus.AVAILABLE);
            seatRepository.save(seat);

            log.error("Payment failed for ticket purchase: eventId={}, userId={}, seatNumber={}, rowNumber={}",
                    event.getId(), user.getId(), ticketRequestDto.getSeatDto().getSeatNumber(),
                    ticketRequestDto.getSeatDto().getRowNumber(), e);

            throw new RuntimeException("Something went wrong"); // TODO: change to paymentProcessingFailed
        }

    }
}
