package com.example.eventManagmentService.services;

import com.example.eventManagmentService.requestDto.EventRequest;
import com.example.eventManagmentService.responseDto.EventResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {
    EventResponse create(EventRequest request);

    EventResponse getById(Long id);

    List<EventResponse> getByVenue(Long venueId);

    List<EventResponse> upcomingEvents();

    EventResponse update(Long id, EventRequest request);

    void delete(Long id);

    Page <EventResponse> get (Pageable pageable);
}
