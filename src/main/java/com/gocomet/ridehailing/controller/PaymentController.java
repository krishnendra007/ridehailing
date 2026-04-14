package com.gocomet.ridehailing.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

    @PostMapping
    public String makePayment(@RequestParam Long rideId) {

        return "Payment successful for ride " + rideId;
    }
}