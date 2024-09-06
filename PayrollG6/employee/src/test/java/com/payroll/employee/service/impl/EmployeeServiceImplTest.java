package com.payroll.employee.service.impl;

import com.payroll.employee.dto.EmployeeDto;
import com.payroll.employee.entity.Employee;
import com.payroll.employee.enums.UserRole;
import com.payroll.employee.mapper.EmployeeMapper;
import com.payroll.employee.repository.EmployeeRepository;
import com.payroll.employee.service.IEmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.swing.text.html.Option;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceImplTest {


    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void initialise(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createEmployee() {
        //mock data
        EmployeeDto employeeDto = new EmployeeDto(123 ,"Virat","Kohli",UserRole.EMPLOYEE,"male","emailAddress@gmail.com",345,"1234567899",new Date(2002-01-01));
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto,new Employee());

        //mocking external calls
        Mockito.when(employeeRepository.findByContactNumber(employeeDto.getContactNumber())).thenReturn(Optional.empty());
        Mockito.when(employeeRepository.save(employee)).thenReturn(null);

        //test call
        employeeService.createEmployee(employeeDto);

        //assertion
        Mockito.verify(employeeRepository).findByContactNumber(Mockito.anyString());
    }


    @Test
    void fetchEmployee() {
        long employeeId=123L;
        Employee employee = new Employee(employeeId,"Dhruv","Goyal", UserRole.EMPLOYEE,"male","dhruv@test.com","ghi",345L,"3456789012",new Date(2002-03-03));
        EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee,new EmployeeDto());

        //mocking external calls
        Mockito.when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.of(employee));

        //testing call
        EmployeeDto fetchedEmployeeDto = employeeService.fetchEmployee(employeeId);

        //assertions
        assertNotNull(fetchedEmployeeDto);
        assertEquals(employeeDto.getFirstName(),fetchedEmployeeDto.getFirstName());
        assertEquals(employeeDto.getLastName(),fetchedEmployeeDto.getLastName());
        assertEquals(employeeDto.getContactNumber(),fetchedEmployeeDto.getContactNumber());
        assertEquals(employeeDto.getEmployeeId(),fetchedEmployeeDto.getEmployeeId());
        assertEquals(employeeDto.getGender(),fetchedEmployeeDto.getGender());
        assertEquals(employeeDto.getEmailAddress(),fetchedEmployeeDto.getEmailAddress());
        assertEquals(employeeDto.getManagerId(),fetchedEmployeeDto.getManagerId());
        assertEquals(employeeDto.getDob(),fetchedEmployeeDto.getDob());

        Mockito.verify(employeeRepository).findByEmployeeId(employeeId);
    }

    @Test
    void updateEmployee() {
        long employeeId=234L;
        Employee employee= new Employee(employeeId,"Prashant","Tiwari", UserRole.EMPLOYEE,"male","prashant@test.com","pqr",789L,"2456378890",new Date(2002-05-05));
        EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee,new EmployeeDto());

        //mock external calls
        Mockito.when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.save(employee)).thenReturn(null);

        //test call
        boolean isUpdated = employeeService.updateEmployee(employeeDto);

        //assertions
        assertTrue(isUpdated);
        Mockito.verify(employeeRepository).findByEmployeeId(employeeId);
        Mockito.verify(employeeRepository).save(employee);
    }

    @Test
    void deleteEmployee() {
        long employeeId=546L;
        Employee employee = new Employee(employeeId,"Kiranjeet","Kaur",UserRole.EMPLOYEE,"female","kiran@test.com","mno",678L,"4567890123",new Date(2002-07-07));

        //mock test calls
        Mockito.when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.of(employee));
        Mockito.doNothing().when(employeeRepository).delete(employee);

        //test call
        boolean isDeleted = employeeService.deleteEmployee(employeeId);

        //assertions
        assertTrue(isDeleted);
        Mockito.verify(employeeRepository).findByEmployeeId(employeeId);
        Mockito.verify(employeeRepository).delete(employee);
    }

    @Test
    void isAvailableEmployee() {
        long employeeId=678L;
        Employee employee = new Employee(employeeId,"Mandeep","Saini",UserRole.EMPLOYEE,"female","mandeep@test.com","xyz",890L,"5678901234",new Date(2002-07-07));

        //mock calls
        Mockito.when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.of(employee));

        //test call
        boolean isAvailable = employeeService.isAvailableEmployee(employeeId);

        //assertions
        assertTrue(isAvailable);
        Mockito.verify(employeeRepository).findByEmployeeId(employeeId);
    }
}