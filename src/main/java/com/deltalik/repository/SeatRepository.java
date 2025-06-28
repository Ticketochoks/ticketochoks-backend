package com.deltalik.repository;

import com.deltalik.entity.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(
            "SELECT s FROM Seat s WHERE s.event.id = :eventId AND " +
                    "s.seatNumber = :seatNumber AND s.rowNumber = :rowNumber"
    )
    Optional<Seat> lockSeatByEventAndPosition(
            @Param("eventId") Long eventId,
            @Param("seatNumber") int seatNumber,
            @Param("rowNumber") int rowNumber
    );
}
