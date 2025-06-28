package com.deltalik.config;

import com.deltalik.entity.VenueLayout;
import com.deltalik.entity.VenueLayoutType;
import com.deltalik.repository.VenueLayoutRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class VenueLayoutInitializer {

    private final VenueLayoutRepository venueLayoutRepository;

    @PostConstruct
    public void init() {
        Set<String> existingLayoutTypes = venueLayoutRepository.findAll().stream()
                .map(VenueLayout::getName)
                .collect(Collectors.toSet());

        List<VenueLayout> missingLayouts = Arrays.stream(VenueLayoutType.values())
                .filter(layoutType -> !existingLayoutTypes.contains(layoutType.getLabel()))
                .map(layoutType -> new VenueLayout(layoutType.getLabel(), layoutType.getRows(), layoutType.getSeatsPerRow()))
                .toList();
        if (!missingLayouts.isEmpty()) {
            venueLayoutRepository.saveAll(missingLayouts);
        }
    }
}
