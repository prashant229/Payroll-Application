package com.payroll.employee.service.clients;

import com.payroll.employee.dto.ResponseDto;
import com.payroll.employee.dto.SalaryDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("salary")
public interface SalaryFeignClient {

    @PostMapping("api/createNewSalary")
    public ResponseEntity<ResponseDto> createNewSalary(@Valid @RequestBody SalaryDto salaryDto);

}
