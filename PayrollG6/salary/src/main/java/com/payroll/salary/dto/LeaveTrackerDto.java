package com.payroll.salary.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveTrackerDto {
    private Long empId;
    private Long casualLeaves;
    private Long sickLeaves;
    private Long earnedLeaves;
    private Long unpaidLeaves;

}