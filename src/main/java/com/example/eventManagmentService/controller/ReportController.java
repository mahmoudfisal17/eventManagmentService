package com.example.eventManagmentService.controller;

import com.example.eventManagmentService.entity.BookingDataReport;
import com.example.eventManagmentService.responseDto.EventBookingReportDto;
import com.example.eventManagmentService.services.BookingDataReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final BookingDataReportService service;

    public ReportController(BookingDataReportService service) {
        this.service = service;
    }

    @GetMapping("/events")
    public List<EventBookingReportDto>  getEventReports() {
        return service.getAllReports();
    }
}