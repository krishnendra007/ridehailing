package com.gocomet.ridehailing.controller;

import com.gocomet.ridehailing.entity.Ride;
import com.gocomet.ridehailing.repository.RideRepository;
import com.gocomet.ridehailing.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;
    private final RideRepository rideRepository;

    @PostMapping
    public Ride createRide(@RequestBody Ride ride) {
        return rideService.createRide(ride);
    }

    @GetMapping("/{id}")
    public Ride getRide(@PathVariable Long id) {
        return rideRepository.findById(id).orElseThrow();
    }
}