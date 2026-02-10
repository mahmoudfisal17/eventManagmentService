package com.example.eventManagmentService.services;

import com.example.eventManagmentService.requestDto.SeatRequest;
import com.example.eventManagmentService.responseDto.SeatResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SeatService {

    SeatResponse create(SeatRequest request);
    SeatResponse update(Long id, SeatRequest request);
    SeatResponse getById(Long id);
    void delete(Long id);
    Page<SeatResponse> list(Pageable pageable);
}
