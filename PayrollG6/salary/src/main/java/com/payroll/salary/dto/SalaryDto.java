package com.payroll.salary.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDto {


    private Long empId;

    @NotNull(message = "HRA is mandatory")
    @Min(value = 0, message = "HRA cannot be negative")
    private Long hra;

    @NotNull(message = "Basic salary is mandatory")
    @Min(value = 0, message = "Basic salary cannot be negative")
    private Long basic;

    @NotNull(message = "Benefits are mandatory")
    @Min(value = 0, message = "Benefits cannot be negative")
    private Long benefits;
}