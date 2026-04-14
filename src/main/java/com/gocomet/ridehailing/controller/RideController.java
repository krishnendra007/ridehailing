package com.gocomet.ridehailing.controller;

import com.gocomet.ridehailing.entity.Ride;
import com.gocomet.ridehailing.repository.RideRepository;
import com.gocomet.ridehailing.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;
    private final RideRepository rideRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @PostMapping
    public Ride createRide(@RequestBody Ride ride) {
        return rideService.createRide(ride);
    }

    @GetMapping("/{id}")
    public Ride getRide(@PathVariable Long id) {
        return rideRepository.findById(id).orElseThrow();
    }

    @GetMapping("/{id}/current-driver")
    public Map<String, Object> getCurrentDriver(@PathVariable Long id) {

        String driverId = redisTemplate.opsForValue()
                .get("ride:current:" + id);

        Map<String, Object> response = new HashMap<>();
        response.put("rideId", id);
        response.put("currentDriverId", driverId);
        response.put("hasDriver", driverId != null);

        return response;
    }
}