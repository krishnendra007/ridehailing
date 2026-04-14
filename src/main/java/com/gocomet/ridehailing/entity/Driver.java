package com.gocomet.ridehailing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "drivers", indexes = {
        @Index(name = "idx_driver_status", columnList = "status")
})
@Getter
@Setter
public class Driver {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private DriverStatus status;
}