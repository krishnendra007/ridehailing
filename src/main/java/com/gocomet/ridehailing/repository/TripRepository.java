package com.gocomet.ridehailing.repository;

import com.gocomet.ridehailing.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}