package com.gocomet.ridehailing.service;

import com.gocomet.ridehailing.dto.CreateDriverRequest;
import com.gocomet.ridehailing.entity.*;
import com.gocomet.ridehailing.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public Driver createDriver(CreateDriverRequest request) {
        Driver driver = new Driver();
        driver.setId(request.getId());
        driver.setName(request.getName());
        driver.setPhoneNumber(request.getPhoneNumber());
        driver.setStatus(DriverStatus.AVAILABLE);
        return driverRepository.save(driver);
    }

    public Driver getDriver(Long driverId) {
        return driverRepository.findById(driverId).orElseThrow();
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public void updateStatus(Long driverId, DriverStatus status) {
        driverRepository.updateDriverStatus(driverId, status);
    }

    @Transactional
    public boolean acceptRide(Long rideId, Long driverId) {

        String current = redisTemplate.opsForValue()
                .get("ride:current:" + rideId);

        if (current == null || !current.equals(driverId.toString())) {
            return false;
        }

        int updated = rideRepository.assignDriver(
                rideId,
                driverId,
                RideStatus.MATCHED,
                RideStatus.REQUESTED
        );

        if (updated == 1) {

            redisTemplate.delete("ride:current:" + rideId);
            redisTemplate.delete("ride:queue:" + rideId);
            redisTemplate.delete("lock:ride:" + rideId);

            return true;
        }

        return false;
    }
}