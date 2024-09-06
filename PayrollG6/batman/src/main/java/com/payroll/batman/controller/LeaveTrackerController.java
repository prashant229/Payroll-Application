package com.payroll.batman.controller;


import com.payroll.batman.dto.LeaveTrackerDto;
import com.payroll.batman.dto.ResponseDto;
import com.payroll.batman.service.ILeaveService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class LeaveTrackerController {

    private ILeaveService iLeaveService;

    @GetMapping("/fetch")
    public ResponseEntity<LeaveTrackerDto> getTracker(@Valid @RequestParam Long empId) {
        LeaveTrackerDto leaveTrackerDto= iLeaveService.getTracker(empId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(leaveTrackerDto);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createTracker(@Valid @RequestBody LeaveTrackerDto leaveTrackerDto){
        iLeaveService.createTracker(leaveTrackerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto("Successfully Created", HttpStatus.CREATED));
    }

    @PostMapping("/initLeavePool")
    public ResponseEntity<ResponseDto> initLeavePool(@RequestParam("empId") Long empId){
        iLeaveService.initLeavePool(empId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto("Successfully Created", HttpStatus.CREATED));
    }
}
