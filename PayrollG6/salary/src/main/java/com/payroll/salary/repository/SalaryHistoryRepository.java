package com.payroll.salary.repository;

import com.payroll.salary.entity.SalaryHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryHistoryRepository extends JpaRepository<SalaryHistoryEntity, Long> {
    List<SalaryHistoryEntity> findByEmpId(Long empId);
}

