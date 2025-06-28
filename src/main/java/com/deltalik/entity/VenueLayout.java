package com.deltalik.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class VenueLayout extends AbstractBaseEntity<Long> {

    @Column(nullable = false)
    private String name;

    private int rows;

    private int seatsPerRow;
}
