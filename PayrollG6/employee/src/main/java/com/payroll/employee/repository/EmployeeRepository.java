package com.payroll.employee.repository;

import com.payroll.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

  Optional<Employee> findByContactNumber(String contactNumber);
  Optional<Employee> findByEmailAddress(String emailAddress);

  Optional<Employee> findByEmployeeId(long employeeId);
}
