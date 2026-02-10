package com.example.eventManagmentService.services;

import com.example.eventManagmentService.responseDto.EventBookingReportDto;

import java.util.List;

public interface BookingDataReportService {

    public List<EventBookingReportDto> getAllReports();
}
