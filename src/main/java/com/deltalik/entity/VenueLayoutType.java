package com.deltalik.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VenueLayoutType {
    SMALL("small", 5, 10),
    MEDIUM("medium", 10, 20),
    LARGE("large", 20, 30),;

    private final String label;
    private final int rows;
    private final int seatsPerRow;
}
