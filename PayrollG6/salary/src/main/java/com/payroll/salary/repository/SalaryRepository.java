package com.payroll.salary.repository;

import com.payroll.salary.entity.SalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<SalaryEntity, Long> {
}
