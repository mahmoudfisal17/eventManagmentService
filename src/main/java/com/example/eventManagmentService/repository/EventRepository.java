package com.example.eventManagmentService.repository;

import com.example.eventManagmentService.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    //    @Query("""
//        SELECT e FROM Event e
//        WHERE e.venue.id = :venueId
//        AND :startTime < e.endTime
//        AND :endTime > e.startTime
//    """)
//    List<Event> findConflictingEvents(
//            @Param("venueId") Long venueId,
//            @Param("startTime") LocalDateTime startTime,
//            @Param("endTime") LocalDateTime endTime
//    );


    List<Event> findByVenueIdAndStartTimeAndEndTime(Long venueId, LocalDateTime startTime,  LocalDateTime endTime);

    List<Event> findByVenueId(Long venueId);

    List<Event> findByStartTimeAfter(LocalDateTime time);
}
