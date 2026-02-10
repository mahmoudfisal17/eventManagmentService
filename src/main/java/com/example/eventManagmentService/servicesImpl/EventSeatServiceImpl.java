package com.example.eventManagmentService.servicesImpl;

import com.example.eventManagmentService.Enum.SeatStatus;
import com.example.eventManagmentService.Enum.SeatType;
import com.example.eventManagmentService.entity.Event;
import com.example.eventManagmentService.entity.EventSeat;
import com.example.eventManagmentService.entity.Seat;
import com.example.eventManagmentService.entity.Venue;
import com.example.eventManagmentService.repository.EventSeatRepository;
import com.example.eventManagmentService.repository.SeatRepository;
import com.example.eventManagmentService.requestDto.ReserveRequest;
import com.example.eventManagmentService.requestDto.UpdateSeatPriceRequest;
import com.example.eventManagmentService.responseDto.EventSeatResponse;
import com.example.eventManagmentService.services.EventSeatService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EventSeatServiceImpl implements EventSeatService {

    private final EventSeatRepository repo;
    private final SeatRepository seatRepository;
    public EventSeatServiceImpl(EventSeatRepository repo, SeatRepository seatRepository) {

        this.repo = repo;
        this.seatRepository = seatRepository;
    }

    private EventSeatResponse map(EventSeat es) {
        return new EventSeatResponse(
                es.getId(),
                es.getEvent().getId(),
                es.getSeat().getId(),
                es.getPrice(),
                es.getStatus().name()
        );
    }

    @Override
    public List<EventSeatResponse> getSeatsForEvent(Long eventId) {
        return repo.findByEventId(eventId).stream().map(this::map).toList();
    }

    @Override
    @Transactional
    public EventSeatResponse updatePrice(Long id, UpdateSeatPriceRequest request) {
        EventSeat es = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        es.setPrice(request.price());
        return map(es);
    }

    // STEP 1 — Reserve seat (temporary hold)
    @Override
    @Transactional
    public EventSeatResponse reserveSeat(Long eventId, Long seatId) {
        EventSeat es = repo.findByEventIdAndSeatId(eventId, seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (es.getStatus() != SeatStatus.AVAILABLE)
            throw new RuntimeException("Seat not available");

        es.setStatus(SeatStatus.RESERVED);
        return map(es);
    }

    // STEP 2 — Confirm booking
    @Override
    @Transactional
    public EventSeatResponse bookSeat(Long eventId, Long seatId) {
        EventSeat es = repo.findByEventIdAndSeatId(eventId, seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (es.getStatus() != SeatStatus.RESERVED)
            throw new RuntimeException("Seat must be reserved first");

        es.setStatus(SeatStatus.BOOKED);
        es = repo.save(es);
        return map(es);
    }

    @Override
    public String cansleReservedSeat(List<ReserveRequest> reservations) {

        for (ReserveRequest reservation : reservations) {

            EventSeat es = repo.findByEventIdAndSeatId(reservation.eventId(), reservation.seatId())
                    .orElseThrow(() -> new RuntimeException("Seat not found"));

            if (es.getStatus() != SeatStatus.RESERVED)
                throw new RuntimeException("Seat must be reserved first");

            es.setStatus(SeatStatus.AVAILABLE);
            repo.save(es);


        }
        return "reserved reservation canceled ";

    }

    @Override
    public void addSeatsForEvent(Event event, Venue venue, BigDecimal price) {

        List<Seat> venueSeats = venue.getSeats();

        for(Seat s : venueSeats) {
            EventSeat es = new EventSeat();
            es.setSeat(s);
            if (!(SeatType.valueOf(s.getSeatType().toString()) == SeatType.REGULAR)) {
                price = price.add(new BigDecimal("10.00")); // fake example

            }
            es.setPrice(price);
            es.setEvent(event);
            es.setStatus(SeatStatus.AVAILABLE);
            repo.save(es);
        }
     }


}
