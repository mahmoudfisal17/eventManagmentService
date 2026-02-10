package com.example.eventManagmentService.responseDto;

import java.math.BigDecimal;

public record EventSeatResponse(
        Long id,
        Long eventId,
        Long seatId,
        BigDecimal price,
        String status
) {}