package com.example.eventManagmentService.requestDto;

import java.math.BigDecimal;

public record UpdateSeatPriceRequest(
        BigDecimal price
) {}