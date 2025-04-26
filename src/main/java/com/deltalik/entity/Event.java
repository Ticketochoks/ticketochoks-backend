package com.deltalik.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
public class Event extends AbstractBaseEntity<Long> {

  @Column(nullable = false, length = 100)
  private String name;

  @Embedded
  private Location location;

  @Column(nullable = false)
  private ZonedDateTime startDateTime;

  @Column(nullable = false)
  private ZonedDateTime endDateTime;

  @Column(nullable = false)
  private String timezone;

  @Min(1)
  @Column(nullable = false)
  private int availableTickets;

  @Min(0)
  @Column(nullable = false)
  private int ticketPrice;
}
