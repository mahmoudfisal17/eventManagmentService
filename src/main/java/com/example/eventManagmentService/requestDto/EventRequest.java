package com.example.eventManagmentService.requestDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventRequest(
        String name,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long venueId,
        BigDecimal price
) {}