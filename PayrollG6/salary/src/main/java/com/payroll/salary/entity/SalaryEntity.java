package com.payroll.salary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="salary")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salaryId;
    private Long empId;
    private Long hra;
    private Long basic;
    private Long benefits;
}
