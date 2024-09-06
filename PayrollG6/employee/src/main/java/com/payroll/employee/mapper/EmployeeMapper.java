package com.payroll.employee.mapper;

import com.payroll.employee.dto.EmployeeDto;
import com.payroll.employee.entity.Employee;

public class EmployeeMapper {

    public static Employee mapToEmployee(EmployeeDto employeeDto, Employee employee){
        employee.setEmployeeId(employeeDto.getEmployeeId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setRole(employeeDto.getRole());
        employee.setGender(employeeDto.getGender());
        employee.setEmailAddress(employeeDto.getEmailAddress());
        employee.setManagerId(employeeDto.getManagerId());
        employee.setContactNumber(employeeDto.getContactNumber());
        employee.setDob(employeeDto.getDob());
        return employee;
    }

    public static EmployeeDto mapToEmployeeDto(Employee employee, EmployeeDto employeeDto){
        employeeDto.setEmployeeId(employee.getEmployeeId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setRole(employee.getRole());
        employeeDto.setGender(employee.getGender());
        employeeDto.setEmailAddress(employee.getEmailAddress());
        employeeDto.setManagerId(employee.getManagerId());
        employeeDto.setContactNumber(employee.getContactNumber());
        employeeDto.setDob(employee.getDob());
        return employeeDto;
    }

}
