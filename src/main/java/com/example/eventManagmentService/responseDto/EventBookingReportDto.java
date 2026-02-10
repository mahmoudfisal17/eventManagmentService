package com.example.eventManagmentService.responseDto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventBookingReportDto {
    private Long eventId;
    private String status;
    private Long totalReservations;
    private BigDecimal totalAmount;
    private LocalDateTime   createdDate;

}
