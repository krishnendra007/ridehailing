package com.gocomet.ridehailing.service;

import com.gocomet.ridehailing.entity.Ride;
import com.gocomet.ridehailing.entity.RideStatus;
import com.gocomet.ridehailing.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final MatchingService matchingService;

    public Ride createRide(Ride request) {

        request.setStatus(RideStatus.REQUESTED);
        request.setCreatedAt(LocalDateTime.now());

        return rideRepository.save(request);
    }
}