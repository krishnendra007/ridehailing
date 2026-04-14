package com.gocomet.ridehailing.service;

import com.gocomet.ridehailing.dto.CreateDriverRequest;
import com.gocomet.ridehailing.entity.*;
import com.gocomet.ridehailing.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
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
        Driver driver = driverRepository.findById(driverId).orElseThrow();
        driver.setStatus(status);
        driverRepository.save(driver);
    }

    @Transactional
    public boolean acceptRide(Long rideId, Long driverId) {

        String lockKey = "lock:ride:" + rideId;

        Boolean lockAcquired = redisTemplate.opsForValue()
                .setIfAbsent(lockKey, driverId.toString(), Duration.ofSeconds(5));

        if (Boolean.FALSE.equals(lockAcquired)) {
            return false;
        }

        try {
            int updated = rideRepository.assignDriver(rideId, driverId);

            if (updated == 1) {
                driverRepository.markDriverBusy(driverId);
                return true;
            }

            return false;

        } finally {
            redisTemplate.delete(lockKey);
        }
    }
}