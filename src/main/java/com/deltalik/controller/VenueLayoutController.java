package com.deltalik.controller;

import com.deltalik.dto.venueLayout.VenueLayoutRequestDto;
import com.deltalik.dto.venueLayout.VenueLayoutResponseDto;
import com.deltalik.service.VenueLayoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/layouts")
@RequiredArgsConstructor
public class VenueLayoutController {

    private final VenueLayoutService venueLayoutService;

    @PostMapping
    public ResponseEntity<VenueLayoutResponseDto> createLayout(@Valid @RequestBody VenueLayoutRequestDto layoutRequestDto) {
        VenueLayoutResponseDto layout = venueLayoutService.createLayout(layoutRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(layout);
    }
}
