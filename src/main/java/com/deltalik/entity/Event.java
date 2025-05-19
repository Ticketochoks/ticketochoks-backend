package com.deltalik.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

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

  @JoinColumn(nullable = false)
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private VenueLayout venueLayout;

  @Min(0)
  @Column(nullable = false)
  private int ticketPrice;
}
