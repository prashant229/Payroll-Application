package com.payroll.batman.dto;



import com.payroll.batman.enums.LeaveStatus;
import com.payroll.batman.enums.LeaveType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveHistoryDto {

    @NotNull(message = "Employee ID is mandatory")
    private Long empId ;

    private Long mangerId ;
    private LeaveType leaveType ;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason ;
    @NotNull(message = "Leave status is mandatory")
    private LeaveStatus status ;
    @Size(max = 500, message = "Rejection reason must be within 500 characters")
    private String rejectionReason ;
    @NotNull(message = "Number of leaves is mandatory")
    @Min(value = 1, message = "Number of leaves must be at least 1")
    private Long noOfLeaves ;
    private String leaveToken ;
}

