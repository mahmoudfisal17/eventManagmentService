package com.example.eventManagmentService.servicesImpl;

import com.example.eventManagmentService.entity.Venue;
import com.example.eventManagmentService.repository.VenueRepository;
import com.example.eventManagmentService.requestDto.VenueRequest;
import com.example.eventManagmentService.responseDto.VenueResponse;
import com.example.eventManagmentService.services.VenueService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;

    @Autowired
    public VenueServiceImpl(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    private static final Function<Venue, VenueResponse> MAP_TO_DTO =
            v -> new VenueResponse(v.getId(), v.getName(), v.getLocation());

    @Override
    @Transactional
    public VenueResponse create(VenueRequest request) {
        Venue v = new Venue();
        v.setName(request.name());
        v.setLocation(request.location());
        v = venueRepository.save(v);
        return MAP_TO_DTO.apply(v);
    }

    @Override
    @Transactional
    public VenueResponse update(Long id, VenueRequest request) {
        Venue v = venueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venue not found: " + id));
        v.setName(request.name());
        v.setLocation(request.location());
        v = venueRepository.save(v);
        return MAP_TO_DTO.apply(v);
    }

    @Override
    @Transactional
    public VenueResponse getById(Long id) {
        Venue v = venueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venue not found: " + id));
        return MAP_TO_DTO.apply(v);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!venueRepository.existsById(id)) {
            throw new IllegalArgumentException("Venue not found: " + id);
        }
        venueRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Page<VenueResponse> list(Pageable pageable) {
        Page<Venue> p = venueRepository.findAll(pageable);
        return p.map(MAP_TO_DTO);
    }
}
