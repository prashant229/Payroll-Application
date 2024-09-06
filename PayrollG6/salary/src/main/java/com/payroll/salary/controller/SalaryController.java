package com.payroll.salary.controller;

import com.payroll.salary.dto.ResponseDto;
import com.payroll.salary.dto.SalaryDto;
import com.payroll.salary.dto.SalaryHistoryDto;
import com.payroll.salary.services.ISalaryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SalaryController {
    @Autowired
    private ISalaryService iSalaryServiceObj;

    //Admin
    @GetMapping("/get")
    public ResponseEntity<List<SalaryHistoryDto>> getSalaryPayrollForAll(
            @Valid
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end){

        List<SalaryHistoryDto> payrollDetails = iSalaryServiceObj.getPayrollDetails(start, end);
        return ResponseEntity.status(HttpStatus.OK).body(payrollDetails);
    }
    //Employee
    @GetMapping("/get/emp")
    public ResponseEntity<List<SalaryHistoryDto>> getSalaryPayrollForEmp(
            @Valid
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam("empId") Long empId) {

        List<SalaryHistoryDto> payrollDetailsForEmp = iSalaryServiceObj.getEmpDetails(start, end, empId);
        return ResponseEntity.status(HttpStatus.OK).body(payrollDetailsForEmp);
    }

    //Admin
    @PostMapping("/createSalary")
    public ResponseEntity<ResponseDto> createSalary(@Valid @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfPayment) {
        iSalaryServiceObj.createSalaryRecord(dateOfPayment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto("Successfully Created", HttpStatus.CREATED));
    }

    @PostMapping("/createNewSalary")
    public ResponseEntity<ResponseDto> createNewSalary(@Valid @RequestBody SalaryDto salaryDto) {
        iSalaryServiceObj.createNewSalaryRecord(salaryDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto("Successfully Created", HttpStatus.CREATED));
    }

    //test purpose
    @GetMapping("/test")    public ResponseEntity<ResponseDto> getTestMsg(){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto("API RUNNING", HttpStatus.CREATED));
    }

}
