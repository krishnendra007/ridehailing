package com.gocomet.ridehailing.service;

import com.gocomet.ridehailing.entity.*;
import com.gocomet.ridehailing.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;

    @Transactional
    public Trip endTrip(Long rideId) {

        Ride ride = rideRepository.findById(rideId).orElseThrow();

        Trip trip = new Trip();
        trip.setRideId(rideId);
        trip.setStartTime(ride.getCreatedAt());
        trip.setEndTime(LocalDateTime.now());
        trip.setFare(100.0);

        ride.setStatus(RideStatus.COMPLETED);
        rideRepository.save(ride);

        driverRepository.markDriverAvailable(ride.getDriverId());

        return tripRepository.save(trip);
    }
}