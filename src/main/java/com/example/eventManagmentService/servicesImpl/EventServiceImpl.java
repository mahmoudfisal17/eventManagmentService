package com.example.eventManagmentService.servicesImpl;

import com.example.eventManagmentService.entity.Event;
 import com.example.eventManagmentService.entity.Venue;
import com.example.eventManagmentService.repository.EventRepository;
import com.example.eventManagmentService.repository.EventSeatRepository;
import com.example.eventManagmentService.repository.SeatRepository;
import com.example.eventManagmentService.repository.VenueRepository;
import com.example.eventManagmentService.requestDto.EventRequest;
import com.example.eventManagmentService.responseDto.EventResponse;
import com.example.eventManagmentService.services.EventSeatService;
import com.example.eventManagmentService.services.EventService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepo;
    private final VenueRepository venueRepo;
    private final EventSeatService eventSeatService;

    public EventServiceImpl(EventRepository eventRepo, VenueRepository venueRepo, EventSeatService eventSeatService) {
        this.eventRepo = eventRepo;
        this.venueRepo = venueRepo;
        this.eventSeatService = eventSeatService;
    }

    private static final Function<Event, EventResponse> MAP = s ->
            new EventResponse(s.getId(), s.getName(), s.getStartTime(),s.getEndTime(),
                    s.getVenue() == null ? null : s.getVenue().getId());


    private EventResponse map(Event e) {
        return new EventResponse(
                e.getId(),
                e.getName(),
                e.getStartTime(),
                e.getEndTime(),
                e.getVenue().getId()
        );
    }

    @Override
    @Transactional
    public EventResponse create(EventRequest request) {


        Venue venue = venueRepo.findById(request.venueId())
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        // no more than events in same venua in same time
        if (!eventRepo.findByVenueIdAndStartTimeAndEndTime(request.venueId(), request.startTime(), request.endTime()).isEmpty()) {
            throw new RuntimeException("Venue already booked for this time!");
        }

        Event e = new Event();
        e.setName(request.name());
        e.setStartTime(request.startTime());
        e.setEndTime(request.endTime());
        e.setVenue(venue);

        eventRepo.save(e);
        addEventSeats(e.getId(),venue.getId(),request.price());
        return map(e);
    }


    @Transactional
    public void addEventSeats(Long eventId, Long venueId, BigDecimal price) {

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Venue venue = venueRepo.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        eventSeatService.addSeatsForEvent(event,venue,price);

         eventRepo.save(event);
    }

    @Override
    public EventResponse getById(Long id) {
        return map(eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found")));
    }

    @Override
    public List<EventResponse> getByVenue(Long venueId) {
        return eventRepo.findByVenueId(venueId).stream().map(this::map).toList();
    }

    @Override
    public List<EventResponse> upcomingEvents() {
        return eventRepo.findByStartTimeAfter(LocalDateTime.now())
                .stream().map(this::map).toList();
    }

    @Override
    @Transactional
    public EventResponse update(Long id, EventRequest request) {
        Event e = eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        e.setName(request.name());
        e.setStartTime(request.startTime());
        e.setEndTime(request.endTime());

        return map(e);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        eventRepo.deleteById(id);
    }

    @Override
    public Page<EventResponse> get(Pageable pageable) {
        return eventRepo.findAll(pageable).map(MAP);
    }
}
