package com.gocomet.ridehailing.service;

import com.gocomet.ridehailing.entity.Ride;
import com.gocomet.ridehailing.entity.RideStatus;
import com.gocomet.ridehailing.repository.DriverRepository;
import com.gocomet.ridehailing.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final MatchingService matchingService;
    private final RedisTemplate<String, String> redisTemplate;
    private final NotificationService notificationService;
    private final DriverRepository driverRepository;

    public Ride createRide(Ride request) {

        request.setStatus(RideStatus.REQUESTED);
        request.setCreatedAt(LocalDateTime.now());

        Ride ride = rideRepository.save(request);

        List<Long> drivers = matchingService.findDrivers(
                request.getPickupLat(),
                request.getPickupLon()
        );

        if (drivers.isEmpty()) {
            notificationService.notifyRider(
                    request.getRiderId(),
                    "No drivers available nearby"
            );
            return ride;
        }

        String queueKey = "ride:queue:" + ride.getId();

        redisTemplate.opsForList().rightPushAll(
                queueKey,
                drivers.stream().map(String::valueOf).toList()
        );

        offerNextDriver(ride.getId());

        return ride;
    }

    public void offerNextDriver(Long rideId) {

        String driverId = redisTemplate.opsForList()
                .leftPop("ride:queue:" + rideId);

        if (driverId == null) {
            notificationService.notifyRider(rideId, "No drivers accepted");
            return;
        }

        Long dId = Long.valueOf(driverId);

        driverRepository.markDriverBusy(dId);

        redisTemplate.opsForValue().set("ride:current:" + rideId, driverId);
        redisTemplate.opsForValue().set("lock:ride:" + rideId, driverId, Duration.ofSeconds(60));

        notificationService.notifyDriver(dId, "Ride " + rideId + " request");

        scheduleTimeout(rideId, driverId);
    }

    @Async
    public void scheduleTimeout(Long rideId, String driverId) {

        try { Thread.sleep(60000); } catch (Exception ignored) {}

        String current = redisTemplate.opsForValue()
                .get("ride:current:" + rideId);

        if (driverId.equals(current)) {

            Long dId = Long.valueOf(driverId);

            driverRepository.markDriverAvailable(dId);

            notificationService.notifyDriver(dId, "Ride expired");

            offerNextDriver(rideId);
        }
    }
}