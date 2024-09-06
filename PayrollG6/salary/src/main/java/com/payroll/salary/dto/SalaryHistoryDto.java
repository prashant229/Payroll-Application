package com.payroll.salary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryHistoryDto {
    private Long empId;
    private Long hra;
    private Long basic;
    private Long benefits;
    private Long paymentAmount;
    private LocalDate dateOfPayment;
    private Long unpaidLeaveDeduction;
}
