package com.payroll.employee.repository;

import com.payroll.employee.dto.EmployeeDto;
import com.payroll.employee.entity.Employee;
import com.payroll.employee.enums.UserRole;
import com.payroll.employee.mapper.EmployeeMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Test
    void findByContactNumber() {
//        EmployeeDto employeeDto = new EmployeeDto(123 ,"Virat","Kohli","test","male","emailAddress@gmail.com",345,"1234567890",new Date(2002-01-01));
//        Employee employee = EmployeeMapper.mapToEmployee(employeeDto,new Employee());
        EmployeeDto employeeDto = new EmployeeDto(456L,"Akshat","Bhardwaj",UserRole.EMPLOYEE,"male","akshat@test.com",789L,"9876543210",new Date(2002-02-02));
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto,new Employee());
        employeeRepository.save(employee);
        Optional<Employee> optionalEmployee= employeeRepository.findByContactNumber("9876543210");
        System.out.println(optionalEmployee.get());
        assertTrue(optionalEmployee.isPresent());
    }

    @Test
    void findByEmailAddress() {
    }

    @Test
    void findByEmployeeId() {
        EmployeeDto employeeDto = new EmployeeDto(456L,"Akshat","Bhardwaj", UserRole.EMPLOYEE,"male","akshat@test.com",789L,"9876543210",new Date(2002-02-02));
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto,new Employee());
        employeeRepository.save(employee);
        Optional<Employee> optionalEmployee= employeeRepository.findByEmployeeId(2);
        System.out.println(optionalEmployee.get());
        assertTrue(optionalEmployee.isPresent());
    }

    @AfterEach
    void tearDown(){
        employeeRepository.deleteAll();
    }
}