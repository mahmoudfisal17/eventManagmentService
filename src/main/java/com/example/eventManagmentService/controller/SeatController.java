package com.example.eventManagmentService.controller;

import com.example.eventManagmentService.requestDto.SeatRequest;
import com.example.eventManagmentService.responseDto.SeatResponse;
import com.example.eventManagmentService.services.SeatService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;
    public SeatController(SeatService seatService) { this.seatService = seatService; }

    @PostMapping
    public ResponseEntity<SeatResponse> create( @RequestBody SeatRequest request) {
        return ResponseEntity.status(201).body(seatService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        seatService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<SeatResponse>> list(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "20") int size,
                                                   @RequestParam(defaultValue = "id,asc") String sort) {
        String[] parts = sort.split(",");
         Pageable p = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(parts[1]), parts[0]));
        return ResponseEntity.ok(seatService.list(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatResponse> update(@PathVariable Long id, @RequestBody SeatRequest request) {
        return ResponseEntity.ok(seatService.update(id, request));
    }

    public ResponseEntity<SeatResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(seatService.getById(id));
    }
}
