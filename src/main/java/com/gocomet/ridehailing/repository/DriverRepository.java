package com.gocomet.ridehailing.repository;

import com.gocomet.ridehailing.entity.Driver;
import com.gocomet.ridehailing.entity.DriverStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Modifying
    @Transactional
    @Query("update Driver d set d.status = 'BUSY' where d.id = :id")
    int markDriverBusy(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update Driver d set d.status = 'AVAILABLE' where d.id = :id")
    int markDriverAvailable(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update Driver d set d.status = :status where d.id = :id")
    int updateDriverStatus(Long id, DriverStatus status);
}