package com.gocomet.ridehailing.service;

import com.gocomet.ridehailing.entity.*;
import com.gocomet.ridehailing.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;

    public Trip endTrip(Long rideId) {

        Ride ride = rideRepository.findById(rideId).orElseThrow();

        Trip trip = new Trip();
        trip.setRideId(rideId);
        trip.setStartTime(ride.getCreatedAt());
        trip.setEndTime(LocalDateTime.now());

        double fare = calculateFare(ride);
        trip.setFare(fare);

        ride.setStatus(RideStatus.COMPLETED);
        rideRepository.save(ride);

        driverRepository.findById(ride.getDriverId()).ifPresent(driver -> {
            driver.setStatus(DriverStatus.AVAILABLE);
            driverRepository.save(driver);
        });

        return tripRepository.save(trip);
    }

    private double calculateFare(Ride ride) {
        double distance = Math.sqrt(
                Math.pow(ride.getPickupLat() - ride.getDropLat(), 2) +
                        Math.pow(ride.getPickupLon() - ride.getDropLon(), 2)
        );

        return 50 + distance * 100;
    }
}