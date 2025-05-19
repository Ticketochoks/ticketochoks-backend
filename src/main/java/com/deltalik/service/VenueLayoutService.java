package com.deltalik.service;

import com.deltalik.dto.venueLayout.SectionRequestDto;
import com.deltalik.dto.venueLayout.VenueLayoutRequestDto;
import com.deltalik.dto.venueLayout.VenueLayoutResponseDto;
import com.deltalik.entity.Section;
import com.deltalik.entity.VenueLayout;
import com.deltalik.mapper.VenueLayoutMapper;
import com.deltalik.repository.VenueLayoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VenueLayoutService {

    private final VenueLayoutRepository venueLayoutRepository;
    private final VenueLayoutMapper venueLayoutMapper;

    @Transactional
    public VenueLayoutResponseDto createLayout(VenueLayoutRequestDto layoutDto) {
        VenueLayout layout = new VenueLayout();
        layout.setName(layoutDto.getName());

        for (SectionRequestDto sectionDto : layoutDto.getSections()) {
            Section section = new Section();
            section.setName(sectionDto.getName());
            section.setRows(sectionDto.getRows());
            section.setSeatsPerRow(sectionDto.getSeatsPerRow());
            section.setVenueLayout(layout);

            layout.getSections().add(section);
        }

        VenueLayout savedLayout = venueLayoutRepository.save(layout);

        return venueLayoutMapper.toVenueLayoutResponseDto(savedLayout);
    }
}
