package com.example.eventManagmentService.services;

import com.example.eventManagmentService.requestDto.VenueRequest;
import com.example.eventManagmentService.responseDto.VenueResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VenueService {

   public VenueResponse create(VenueRequest request);
    public VenueResponse update(Long id, VenueRequest request);
    public  VenueResponse getById(Long id);
    public  void delete(Long id);
    public Page<VenueResponse> list(Pageable pageable);
}
