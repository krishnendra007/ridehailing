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

    public List<Long> findDrivers(double lat, double lon) {

        List<String> nearbyDrivers = locationService.findNearbyDrivers(lat, lon);

        return nearbyDrivers.stream()
                .map(Long::valueOf)
                .toList();
    }
}