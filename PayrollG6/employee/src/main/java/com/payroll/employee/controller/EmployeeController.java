package com.payroll.employee.controller;

import com.payroll.employee.dto.EmployeeDto;
import com.payroll.employee.dto.ResponseDto;
import com.payroll.employee.entity.Employee;
import com.payroll.employee.service.IEmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/employee")
//@AllArgsConstructor
public class EmployeeController {

    @Value("${build.version}")
    private String buildVersion;
    @Autowired
    private IEmployeeService iEmployeeService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto){

        iEmployeeService.createEmployee(employeeDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto("Created Success",HttpStatus.CREATED));
    }

    @GetMapping("/fetch")
    public ResponseEntity<EmployeeDto> fetchEmployee(@Valid @RequestParam long employeeId){
        EmployeeDto employeeDto = iEmployeeService.fetchEmployee(employeeId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateEmployee(@Valid @RequestBody EmployeeDto employeeDto){
        boolean isUpdated = iEmployeeService.updateEmployee(employeeDto);

        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto("Employee Updated Successfully",HttpStatus.ACCEPTED));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto("Unable to update employee",HttpStatus.BAD_REQUEST));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteEmployee(@Valid @RequestParam long employeeId){
        boolean isDeleted = iEmployeeService.deleteEmployee(employeeId);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto("Employee deleted Successfully",HttpStatus.OK));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto("Unable to delete",HttpStatus.BAD_REQUEST));
        }
    }

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/{employee-id}")
    public boolean isEmployeeAvailable(@PathVariable("employee-id") long employeeId){
        return iEmployeeService.isAvailableEmployee(employeeId);
    }

    @GetMapping("/fetchEmployee")
    public ResponseEntity<EmployeeDto> fetchEmployeeDetails(@Valid @RequestParam String emailAddress){
        EmployeeDto employeeDto = iEmployeeService.fetchEmployeeDetails(emailAddress);

        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeDto);
    }
}
