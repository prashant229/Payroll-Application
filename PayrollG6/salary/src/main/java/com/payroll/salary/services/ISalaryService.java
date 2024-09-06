package com.payroll.salary.services;

import com.payroll.salary.dto.SalaryDto;
import com.payroll.salary.dto.SalaryHistoryDto;

import java.time.LocalDate;
import java.util.List;

public interface ISalaryService {
    List<SalaryHistoryDto> getPayrollDetails(LocalDate start, LocalDate end);

    List<SalaryHistoryDto> getEmpDetails(LocalDate startDate, LocalDate endDate, Long empId);

    void createSalaryRecord(LocalDate dateOfPayment);

    void createNewSalaryRecord(SalaryDto salaryDto);
}
