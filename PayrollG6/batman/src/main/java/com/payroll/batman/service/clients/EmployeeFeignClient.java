package com.payroll.batman.service.clients;

import com.payroll.batman.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("employee")
public interface EmployeeFeignClient {

    @GetMapping("/api/employee/{employeeId}")
    boolean isEmployeeAvailable(@PathVariable("employeeId") long employeeId);

    @GetMapping("/api/employee/fetch")
    public ResponseEntity<EmployeeDto> fetchEmployee(@RequestParam long employeeId);
}
