package com.example.eventManagmentService.servicesImpl;

import com.example.eventManagmentService.entity.BookingDataReport;
import com.example.eventManagmentService.repository.BookingDataReportRepository;
import com.example.eventManagmentService.responseDto.EventBookingReportDto;
import com.example.eventManagmentService.services.BookingDataReportService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingDataReportServiceImo implements BookingDataReportService {

   private final BookingDataReportRepository  bookingDataReportRepository;

    public BookingDataReportServiceImo(BookingDataReportRepository bookingDataReportRepository) {
        this.bookingDataReportRepository = bookingDataReportRepository;
    }

    @Override
    @Cacheable(value = "eventReports")
    public List<EventBookingReportDto> getAllReports() {
        System.out.println("Fetching reports from DB");
        List<BookingDataReport> entities = bookingDataReportRepository.findAll();
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EventBookingReportDto toDto(BookingDataReport e) {
        return new EventBookingReportDto(
                e.getEventId(),
                e.getStatus(),
                e.getTotalReservations(),
                e.getTotalAmount(),
                e.getCreatedDate());

    }
}
