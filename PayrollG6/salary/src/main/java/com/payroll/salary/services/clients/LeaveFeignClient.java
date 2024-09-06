package com.payroll.salary.services.clients;//package com.payroll.salary.services.clients;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import com.payroll.salary.dto.LeaveTrackerDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient("leaves")
public interface LeaveFeignClient {

    @GetMapping("/api/fetch")
    LeaveTrackerDto getTracker(@RequestParam Long empId);

    @GetMapping("/api/getUnpaidLeaves")
    public  ResponseEntity<Long> getUnpaidLeaves(@Valid @RequestParam(value ="empId") Long empId ,
                                                 @RequestParam(value ="from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from ,
                                                 @RequestParam(value ="to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to);
}
