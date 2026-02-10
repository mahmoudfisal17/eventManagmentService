package com.example.eventManagmentService.servicesImpl;

import com.example.eventManagmentService.Enum.SeatType;
import com.example.eventManagmentService.entity.Seat;
import com.example.eventManagmentService.entity.Venue;
import com.example.eventManagmentService.repository.SeatRepository;
import com.example.eventManagmentService.repository.VenueRepository;
import com.example.eventManagmentService.requestDto.SeatRequest;
import com.example.eventManagmentService.responseDto.SeatResponse;
import com.example.eventManagmentService.services.SeatService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SeatServiceImpl  implements SeatService {

    private final SeatRepository seatRepository;
    private final VenueRepository venueRepository;

    public SeatServiceImpl(SeatRepository seatRepository, VenueRepository venueRepository) {
        this.seatRepository = seatRepository;
        this.venueRepository = venueRepository;
    }

    private static final Function<Seat, SeatResponse> MAP = s ->
            new SeatResponse(s.getId(), s.getRowNumber(), s.getSeatNumber(),
                    s.getSeatType() == null ? null : s.getSeatType().name(),
                    s.getVenue() == null ? null : s.getVenue().getId());

    @Override
    @Transactional
    public SeatResponse create(SeatRequest request) {
        Seat s = new Seat();
        s.setRowNumber(request.rowNumber());
        s.setSeatNumber(request.seatNumber());
     if (request.seatType() != null)
         s.setSeatType(SeatType.valueOf(request.seatType()));
        if (request.venueId() != null) {
            Venue v = venueRepository.findById(request.venueId()).orElseThrow(() -> new IllegalArgumentException("Venue not found"));
            s.setVenue(v);
        }
        s = seatRepository.save(s);
        return MAP.apply(s);
    }

    @Override
    @Transactional
    public SeatResponse update(Long id, SeatRequest request) {
        Seat s = seatRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Seat not found"));
        s.setRowNumber(request.rowNumber());
        s.setSeatNumber(request.seatNumber());
      if (request.seatType() != null)
          s.setSeatType(SeatType.valueOf(request.seatType()));
        if (request.venueId() != null) {
            Venue v = venueRepository.findById(request.venueId()).orElseThrow(() -> new IllegalArgumentException("Venue not found"));
            s.setVenue(v);
        }
        s = seatRepository.save(s);
        return MAP.apply(s);
    }

    @Override
    @Transactional
    public SeatResponse getById(Long id) {
        return MAP.apply(seatRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Seat not found")));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!seatRepository.existsById(id)) throw new IllegalArgumentException("Seat not found");
        seatRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Page<SeatResponse> list(Pageable pageable) {
        return seatRepository.findAll(pageable).map(MAP);
    }
}





