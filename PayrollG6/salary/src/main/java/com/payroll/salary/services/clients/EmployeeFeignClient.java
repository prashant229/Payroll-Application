package com.payroll.salary.services.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("employee")
public interface EmployeeFeignClient {

    @GetMapping("/api/employee/{employeeId}")
    boolean isEmployeeAvailable(@PathVariable("employeeId") long employeeId);
}
