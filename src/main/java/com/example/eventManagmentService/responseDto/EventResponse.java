package com.example.eventManagmentService.responseDto;

import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String name,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long venueId)
{}