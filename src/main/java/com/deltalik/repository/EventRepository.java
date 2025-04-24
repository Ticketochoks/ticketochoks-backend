package com.deltalik.repository;

import com.deltalik.entity.Event;
import com.deltalik.entity.Location;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

  @Query("select e from Event e where " +
      "e.location.street = :#{#location.street} AND " +
      "e.location.city = :#{#location.city} AND " +
      "e.startDateTime < :endTime AND " +
      "e.endDateTime > :startTime")
  Optional<Event> findByLocationAndTimeOverlap(
      @Param("location") Location location,
      @Param("startTime") ZonedDateTime startTime,
      @Param("endTime") ZonedDateTime endTime);
}
