package com.example.eventManagmentService.entity;
import com.example.eventManagmentService.Enum.SeatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rowNumber;
    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;   // VIP, REGULAR...


    @ManyToOne(fetch = FetchType.LAZY)
    private Venue venue;
}
