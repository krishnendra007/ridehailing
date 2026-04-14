package com.gocomet.ridehailing.controller;

import com.gocomet.ridehailing.entity.Trip;
import com.gocomet.ridehailing.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping("/{id}/end")
    public Trip endTrip(@PathVariable Long id) {
        return tripService.endTrip(id);
    }
}