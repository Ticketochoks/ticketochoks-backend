package com.deltalik.repository;

import com.deltalik.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByEventIdAndSeatNumberAndRowNumber(Long eventId, int seatNumber, int rowNumber);
}
