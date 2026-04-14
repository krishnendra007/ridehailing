package com.gocomet.ridehailing.service;

import com.gocomet.ridehailing.entity.Driver;
import com.gocomet.ridehailing.entity.DriverStatus;
import com.gocomet.ridehailing.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final DriverLocationService locationService;
    private final DriverRepository driverRepository;

    public Long findDriver(double lat, double lon) {

        List<String> nearbyDrivers = locationService.findNearbyDrivers(lat, lon);

        for (String id : nearbyDrivers) {
            Driver driver = driverRepository.findById(Long.valueOf(id)).orElse(null);

            if (driver != null && driver.getStatus() == DriverStatus.AVAILABLE) {
                return driver.getId();
            }
        }

        return null;
    }
}