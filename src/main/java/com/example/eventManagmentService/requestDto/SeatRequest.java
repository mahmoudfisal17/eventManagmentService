package com.example.eventManagmentService.requestDto;

public record SeatRequest(Integer rowNumber, Integer seatNumber, String seatType, Long venueId) {}
