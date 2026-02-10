package com.example.eventManagmentService.repository;

import com.example.eventManagmentService.Enum.SeatStatus;
import com.example.eventManagmentService.entity.EventSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventSeatRepository  extends JpaRepository<EventSeat, Long> {

    Optional<EventSeat> findByEventIdAndSeatId(Long eventId, Long seatId);

    List<EventSeat> findByEventId(Long eventId);

    List<EventSeat> findByEventIdAndStatus(Long eventId, SeatStatus status);

   // List<EventSeat> findByVenueId(Long venueId);
}