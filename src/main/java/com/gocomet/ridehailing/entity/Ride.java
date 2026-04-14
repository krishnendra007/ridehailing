package com.gocomet.ridehailing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "rides", indexes = {
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_driver", columnList = "driverId")
})
@Getter @Setter
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long riderId;

    private Double pickupLat;
    private Double pickupLon;

    private Double dropLat;
    private Double dropLon;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    private Long driverId;

    private LocalDateTime createdAt;
}