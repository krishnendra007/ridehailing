package com.gocomet.ridehailing.repository;

import com.gocomet.ridehailing.entity.Ride;
import com.gocomet.ridehailing.entity.RideStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RideRepository extends JpaRepository<Ride, Long> {

    @Modifying
    @Transactional
    @Query("""
        UPDATE Ride r
        SET r.driverId = :driverId, r.status = :status
        WHERE r.id = :rideId AND r.status = :expectedStatus
    """)
    int assignDriver(@Param("rideId") Long rideId,
                     @Param("driverId") Long driverId,
                     @Param("status") RideStatus status,
                     @Param("expectedStatus") RideStatus expectedStatus);
}