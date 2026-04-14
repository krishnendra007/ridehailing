package com.gocomet.ridehailing.dto;

import lombok.Data;

@Data
public class RideRequest {
    private Long riderId;
    private double pickupLat;
    private double pickupLon;
    private double dropLat;
    private double dropLon;
}