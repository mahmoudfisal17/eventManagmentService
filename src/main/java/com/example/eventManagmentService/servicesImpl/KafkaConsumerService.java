package com.example.eventManagmentService.servicesImpl;

import com.example.eventManagmentService.entity.BookingDataReport;
import com.example.eventManagmentService.repository.BookingDataReportRepository;
import com.example.eventManagmentService.services.BookingDataReportService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.eventManagmentService.responseDto.EventBookingReportDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class KafkaConsumerService {

    private final BookingDataReportRepository bookingDataReportRepository;

    public KafkaConsumerService(BookingDataReportRepository bookingDataReportRepository) {
        this.bookingDataReportRepository = bookingDataReportRepository;
    }

    @KafkaListener(topics = "event-status-report")
    @CacheEvict(value = "eventReports", allEntries = true)

    public void consume(String message) {
        System.out.println("Received: " + message);

        ObjectMapper mapper = new ObjectMapper();


        try {
            List<EventBookingReportDto> list = mapper.readValue(
                    message,
                    new TypeReference<List<EventBookingReportDto>>() {

                    }
            );

            list.forEach(System.out::println);

            for (EventBookingReportDto dto : list) {
                BookingDataReport entity = new BookingDataReport();
                entity.setEventId(dto.getEventId());
                entity.setStatus(dto.getStatus());
                entity.setTotalReservations(dto.getTotalReservations());
                entity.setTotalAmount(dto.getTotalAmount());
                entity.setCreatedDate(LocalDateTime.now());

                bookingDataReportRepository.save(entity);  // save each entity
            }

        } catch (Exception e) {
            System.err.println("Error parsing message: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
