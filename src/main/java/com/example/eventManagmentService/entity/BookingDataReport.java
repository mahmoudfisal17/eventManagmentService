package com.example.eventManagmentService.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class BookingDataReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 2)
    private Long eventId;

    private String status;

    private Long totalReservations;

    private BigDecimal totalAmount;

    private  LocalDateTime createdDate;

}