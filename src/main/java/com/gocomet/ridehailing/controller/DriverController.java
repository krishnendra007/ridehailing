package com.gocomet.ridehailing.controller;

import com.gocomet.ridehailing.dto.CreateDriverRequest;
import com.gocomet.ridehailing.entity.Driver;
import com.gocomet.ridehailing.entity.DriverStatus;
import com.gocomet.ridehailing.service.DriverLocationService;
import com.gocomet.ridehailing.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverLocationService locationService;
    private final DriverService driverService;

    @PostMapping
    public Driver createDriver(@RequestBody CreateDriverRequest request) {
        return driverService.createDriver(request);
    }

    @GetMapping("/{id}")
    public Driver getDriver(@PathVariable Long id) {
        return driverService.getDriver(id);
    }

    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @PostMapping("/{id}/location")
    public String updateLocation(@PathVariable Long id,
                                 @RequestParam double lat,
                                 @RequestParam double lon) {
        locationService.updateLocation(id, lat, lon);
        return "updated";
    }

    @PostMapping("/{id}/accept")
    public String acceptRide(@PathVariable Long id,
                             @RequestParam Long rideId) {

        boolean success = driverService.acceptRide(rideId, id);

        return success ? "accepted" : "failed";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam DriverStatus status) {
        driverService.updateStatus(id, status);
        return "status updated";
    }
}