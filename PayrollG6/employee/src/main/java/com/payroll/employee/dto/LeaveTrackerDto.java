package com.payroll.employee.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class LeaveTrackerDto {
    @NotNull(message = "Employee ID is mandatory")
    private Long empId;

    @Min(value = 0, message = "Casual leaves cannot be negative")
    private Long casualLeaves;
    @Min(value = 0, message = "Sick leaves cannot be negative")
    private Long sickLeaves;
    @Min(value = 0, message = "Earned leaves cannot be negative")
    private Long earnedLeaves;
    @Min(value = 0, message = "Unpaid leaves cannot be negative")
    private Long unpaidLeaves;

}
