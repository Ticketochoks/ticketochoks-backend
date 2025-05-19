package com.deltalik.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true, exclude = "venueLayout")
public class Section extends AbstractBaseEntity<Long> {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_layout_id")
    private VenueLayout venueLayout;

    private Integer rows;
    private Integer seatsPerRow;
}
