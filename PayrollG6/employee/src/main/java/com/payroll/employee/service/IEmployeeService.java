package com.payroll.employee.service;

import com.payroll.employee.dto.EmployeeDto;

public interface IEmployeeService {

    void createEmployee(EmployeeDto employeeDto);
    EmployeeDto fetchEmployee(long employeeId);

    boolean updateEmployee(EmployeeDto employeeDto);
    boolean deleteEmployee(long employeeId);

    boolean isAvailableEmployee(long employeeId);

    EmployeeDto fetchEmployeeDetails(String userEmail);
}
