package com.payroll.message.dto;

import com.payroll.message.enums.LeaveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @AllArgsConstructor @NoArgsConstructor
public class LeaveMessageDto {
    private Long empId ;
    private String employeeName ;
    private String managerEmail ;
    private LeaveType leaveType ;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason ;
}
