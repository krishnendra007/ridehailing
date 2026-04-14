package com.gocomet.ridehailing.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void notifyDriver(Long driverId, String message) {
        System.out.println(" DRIVER " + driverId + ": " + message);
    }

    public void notifyRider(Long riderId, String message) {
        System.out.println(" RIDER " + riderId + ": " + message);
    }
}