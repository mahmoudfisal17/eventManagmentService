package com.example.eventManagmentService.responseDto;

public record SeatResponse(Long id, Integer rowNumber, Integer seatNumber, String seatType, Long venueId) {}

