package com.example.eventManagmentService.repository;

import com.example.eventManagmentService.entity.BookingDataReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDataReportRepository extends JpaRepository<BookingDataReport, Long> {
}
