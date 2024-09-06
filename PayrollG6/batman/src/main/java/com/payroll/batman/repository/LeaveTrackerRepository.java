package com.payroll.batman.repository;

import com.payroll.batman.entity.LeaveTrackerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaveTrackerRepository extends JpaRepository<LeaveTrackerEntity,Long> {
    Optional<LeaveTrackerEntity> findByEmpId(Long empId);
}


