package com.gocomet.ridehailing.repository;

import com.gocomet.ridehailing.entity.Driver;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    @Modifying
    @Transactional
    @Query("""
    UPDATE Driver d
    SET d.status = 'BUSY'
    WHERE d.id = :driverId AND d.status = 'AVAILABLE'
""")
    int markDriverBusy(Long driverId);
}