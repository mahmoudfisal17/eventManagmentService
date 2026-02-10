package com.example.eventManagmentService.controller;

import com.example.eventManagmentService.requestDto.ReserveRequest;
import com.example.eventManagmentService.requestDto.UpdateSeatPriceRequest;
import com.example.eventManagmentService.responseDto.EventSeatResponse;
import com.example.eventManagmentService.services.EventSeatService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/event-seats")
public class EventSeatController {

    private final EventSeatService service;

    public EventSeatController(EventSeatService service) {
        this.service = service;
    }

    @GetMapping("/event/{eventId}")
    public List<EventSeatResponse> list(@PathVariable Long eventId) {
        return service.getSeatsForEvent(eventId);
    }

    @PutMapping("/{id}/price")
    public EventSeatResponse updatePrice(@PathVariable Long id,
                                         @RequestBody UpdateSeatPriceRequest req) {
        return service.updatePrice(id, req);
    }

    @PostMapping("/reserve")
    public EventSeatResponse reserve(@RequestBody ReserveRequest reserveRequest) {
        return service.reserveSeat(reserveRequest.eventId(), reserveRequest.seatId());
    }

    @PostMapping("/bookSeat")
    public EventSeatResponse book(@RequestBody ReserveRequest reserveRequest) {
        return service.bookSeat(reserveRequest.eventId(), reserveRequest.seatId());
    }

    @PostMapping("/cansleReservedSeat")
    public String cansleReservedSeat(@RequestBody List<ReserveRequest> reserveRequest) {
        return service.cansleReservedSeat(reserveRequest);
    }


}