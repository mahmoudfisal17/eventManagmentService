package com.example.eventManagmentService.services;

import com.example.eventManagmentService.entity.Event;
import com.example.eventManagmentService.entity.Venue;
import com.example.eventManagmentService.requestDto.ReserveRequest;
import com.example.eventManagmentService.requestDto.UpdateSeatPriceRequest;
import com.example.eventManagmentService.responseDto.EventSeatResponse;

import java.math.BigDecimal;
import java.util.List;

public interface EventSeatService {

    List<EventSeatResponse> getSeatsForEvent(Long eventId);

    EventSeatResponse updatePrice(Long eventSeatId, UpdateSeatPriceRequest request);

    EventSeatResponse reserveSeat(Long eventId, Long seatId);

    EventSeatResponse bookSeat(Long eventId, Long seatId);

    String cansleReservedSeat(List<ReserveRequest> reservations);


    void addSeatsForEvent(Event event, Venue venue, BigDecimal price);
}
