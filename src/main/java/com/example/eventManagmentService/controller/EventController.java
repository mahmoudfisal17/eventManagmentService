package com.example.eventManagmentService.controller;

import com.example.eventManagmentService.requestDto.EventRequest;
import com.example.eventManagmentService.responseDto.EventResponse;
import com.example.eventManagmentService.responseDto.SeatResponse;
import com.example.eventManagmentService.services.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping
    public EventResponse create(@RequestBody EventRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public EventResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public ResponseEntity<Page<EventResponse>> list(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "20") int size,
                                                   @RequestParam(defaultValue = "id,asc") String sort) {
        String[] parts = sort.split(",");
        Pageable p = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(parts[1]), parts[0]));
        return ResponseEntity.ok(service.get(p));
    }


    @GetMapping("/venue/{venueId}")
    public List<EventResponse> byVenue(@PathVariable Long venueId) {
        return service.getByVenue(venueId);
    }

    @GetMapping("/upcoming")
    public List<EventResponse> upcoming() {
        return service.upcomingEvents();
    }

    @PutMapping("/{id}")
    public EventResponse update(@PathVariable Long id, @RequestBody EventRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}