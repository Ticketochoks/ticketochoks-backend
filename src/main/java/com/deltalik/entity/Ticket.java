package com.deltalik.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Ticket extends AbstractBaseEntity<Long> {

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Enumerated(value = EnumType.STRING)
  @Column(nullable = false)
  private TicketStatus status;

  @Column(nullable = true)
  private ZonedDateTime purchaseDateTime;

  @ManyToOne
  @JoinColumn(name = "seat_id")
  private Seat seat;
}
