package com.payroll.salary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="salary_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salaryHistoryId;
    private Long empId;
    private Long hra;
    private Long basic;
    private Long benefits;
    private LocalDate dateOfPayment;
    private Long paymentAmount;
    private Long unpaidLeaveDeduction;
}
