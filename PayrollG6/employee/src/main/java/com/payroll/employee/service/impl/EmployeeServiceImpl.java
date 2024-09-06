package com.payroll.employee.service.impl;

import com.payroll.employee.Exceptions.EmployeeAlreadyExistsException;
import com.payroll.employee.Exceptions.ResourceNotFoundException;
import com.payroll.employee.dto.EmployeeDto;
import com.payroll.employee.entity.Employee;
import com.payroll.employee.mapper.EmployeeMapper;
import com.payroll.employee.repository.EmployeeRepository;
import com.payroll.employee.service.IEmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {
    private EmployeeRepository employeeRepository;

    @Override
    public void createEmployee(EmployeeDto employeeDto) {
        String contactNumber = employeeDto.getContactNumber();

        Optional<Employee> foundEmployee = employeeRepository.findByContactNumber(contactNumber);

        if(foundEmployee.isPresent()){
            throw new EmployeeAlreadyExistsException("Employee Already Exists for given mobile number " + contactNumber);
        }

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto,new Employee());

        employeeRepository.save(employee);
    }

    @Override
    public EmployeeDto fetchEmployee(long employeeId) {

        Employee employee = employeeRepository.findByEmployeeId(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "Employee-Id", String.valueOf(employeeId))
        );

        EmployeeDto employeeDto=EmployeeMapper.mapToEmployeeDto(employee,new EmployeeDto());

        return employeeDto;
    }

    @Override
    public boolean updateEmployee(EmployeeDto employeeDto) {
        boolean isUpdated =false;

        Employee employee = employeeRepository.findByEmployeeId(employeeDto.getEmployeeId()).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "EmployeeId", String.valueOf(employeeDto.getEmployeeId()))
        );

        if(employee != null){
            EmployeeMapper.mapToEmployee(employeeDto,employee);
            employeeRepository.save(employee);

            isUpdated=true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteEmployee(long employeeId) {
            boolean deleted = false;
            Employee employee=employeeRepository.findByEmployeeId(employeeId).orElseThrow(
                    () ->new ResourceNotFoundException("Employee", "Employee Id", String.valueOf(employeeId))
            );
            if(employee!=null){
            employeeRepository.delete(employee);
            deleted=true;
            }
            return deleted;
    }

    @Override
    public boolean isAvailableEmployee(long employeeId) {
        Optional<Employee> foundEmployee = employeeRepository.findByEmployeeId(employeeId);

        return foundEmployee.isPresent();
    }

    @Override
    public EmployeeDto fetchEmployeeDetails(String userEmail) {

        Employee employee = employeeRepository.findByEmailAddress(userEmail).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "Email", userEmail)
        );

        EmployeeDto employeeDto=EmployeeMapper.mapToEmployeeDto(employee,new EmployeeDto());
        return employeeDto;
    }
}
