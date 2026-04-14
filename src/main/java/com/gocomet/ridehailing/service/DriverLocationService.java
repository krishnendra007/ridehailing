package com.gocomet.ridehailing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverLocationService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String GEO_KEY = "drivers";

    public void updateLocation(Long driverId, double lat, double lon) {
        redisTemplate.opsForGeo()
                .add(GEO_KEY, new Point(lon, lat), driverId.toString());
    }

    public List<String> findNearbyDrivers(double lat, double lon) {

        GeoResults<RedisGeoCommands.GeoLocation<String>> results =
                redisTemplate.opsForGeo()
                        .radius(GEO_KEY,
                                new Circle(
                                        new Point(lon, lat),
                                        new Distance(5000000, Metrics.MILES)
                                ));

        return results.getContent()
                .stream()
                .map(r -> r.getContent().getName())
                .toList();
    }
}