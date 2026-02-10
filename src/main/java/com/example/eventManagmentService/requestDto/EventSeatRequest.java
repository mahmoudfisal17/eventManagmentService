package com.example.eventManagmentService.requestDto;

import java.math.BigDecimal;

public record EventSeatRequest(
        Long eventId,
        Long seatId,
        BigDecimal price
) {}