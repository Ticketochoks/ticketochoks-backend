package com.deltalik.repository;

import com.deltalik.entity.VenueLayout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VenueLayoutRepository extends JpaRepository<VenueLayout, Long> {
    Optional<VenueLayout> findByName(String name);
}
