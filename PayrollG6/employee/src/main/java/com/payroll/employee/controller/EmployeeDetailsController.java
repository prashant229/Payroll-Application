package com.payroll.employee.controller;

import com.payroll.employee.dto.EmployeeDetailsDto;
import com.payroll.employee.dto.EmployeeDto;
import com.payroll.employee.dto.ResponseDto;
import com.payroll.employee.dto.SalaryDto;
import com.payroll.employee.entity.Employee;
import com.payroll.employee.service.IEmployeeDetailsService;
import com.payroll.employee.service.IEmployeeService;
import com.payroll.employee.service.clients.LeaveFeignClient;
import com.payroll.employee.service.clients.SalaryFeignClient;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class EmployeeDetailsController {

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private LeaveFeignClient leaveFeignClient;

    @Autowired
    private SalaryFeignClient salaryFeignClient;

//    private IEmployeeDetailsService iEmployeeDetailsService;

//    @GetMapping("fetch-employee-details")
//    public ResponseEntity<EmployeeDetailsDto> getEmployeeDetails(@RequestParam long employeeId){
//         EmployeeDetailsDto employeeDetailsDto = iEmployeeDetailsService.fetchEmployeeDetails(employeeId);
//    }

    @PostMapping("/createEmp")
    public ResponseEntity<ResponseDto> createEmployee(@Valid @RequestBody EmployeeDetailsDto employeeDetailsDto){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployeeId(employeeDetailsDto.getEmployeeId());
        employeeDto.setFirstName(employeeDetailsDto.getFirstName());
        employeeDto.setLastName(employeeDetailsDto.getLastName());
        employeeDto.setContactNumber(employeeDetailsDto.getContactNumber());
        employeeDto.setEmailAddress(employeeDetailsDto.getEmailAddress());
        employeeDto.setRole(employeeDetailsDto.getRole());
        employeeDto.setGender(employeeDetailsDto.getGender());
        employeeDto.setManagerId(employeeDetailsDto.getManagerId());

        iEmployeeService.createEmployee(employeeDto);
        EmployeeDto emp = iEmployeeService.fetchEmployeeDetails(employeeDetailsDto.getEmailAddress());
        SalaryDto salaryDto = new SalaryDto();

        salaryDto.setEmpId(emp.getEmployeeId());
        salaryDto.setBasic(employeeDetailsDto.getBasic());
        salaryDto.setHra(employeeDetailsDto.getHra());
        salaryDto.setBenefits(employeeDetailsDto.getBenefits());

        salaryFeignClient.createNewSalary(salaryDto);
        leaveFeignClient.initLeavePool(emp.getEmployeeId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto("Created Success",HttpStatus.CREATED));
    }
}
