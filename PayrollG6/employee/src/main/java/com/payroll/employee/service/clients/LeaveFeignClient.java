package com.payroll.employee.service.clients;

import com.payroll.employee.dto.LeaveTrackerDto;
import com.payroll.employee.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient("leaves")
public interface LeaveFeignClient {
    @GetMapping("/api/fetch")
    LeaveTrackerDto getTracker(@RequestParam Long empId);

    @PostMapping("api/initLeavePool")
    public ResponseEntity<ResponseDto> initLeavePool(@RequestParam("empId") Long empId);
}
