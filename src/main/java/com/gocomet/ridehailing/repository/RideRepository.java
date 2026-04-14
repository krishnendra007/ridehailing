package com.gocomet.ridehailing.repository;

import com.gocomet.ridehailing.entity.Ride;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RideRepository extends JpaRepository<Ride, Long> {

    @Modifying
    @Transactional
    @Query("""
                UPDATE Ride r
                SET r.driverId = :driverId, r.status = 'MATCHED'
                WHERE r.id = :rideId AND r.status = 'REQUESTED'
            """)
    int assignDriver(Long rideId, Long driverId);
}