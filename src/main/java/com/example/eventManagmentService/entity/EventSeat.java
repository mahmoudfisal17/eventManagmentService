package com.example.eventManagmentService.entity;

import com.example.eventManagmentService.Enum.SeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "event_seat",
        uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "seat_id"}))

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    // Which Physical Seat
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;


    // Dynamic price for this event only
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    // Seat state for THIS EVENT
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status; // AVAILABLE / RESERVED / BOOKED


}
