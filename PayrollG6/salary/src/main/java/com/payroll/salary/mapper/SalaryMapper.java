package com.payroll.salary.mapper;

import com.payroll.salary.dto.SalaryDto;
import com.payroll.salary.entity.SalaryEntity;


public class SalaryMapper {
    public static SalaryEntity mapToSalary(SalaryDto salaryDto, SalaryEntity salary){
        salary.setBasic(salaryDto.getBasic());
        salary.setHra(salaryDto.getHra());
        salary.setBenefits(salaryDto.getBenefits());
        salary.setEmpId(salaryDto.getEmpId());
        return salary;
    }

    public static SalaryDto mapToSalaryDto(SalaryEntity salary, SalaryDto salaryDto) {
        salaryDto.setBasic(salary.getBasic());
        salaryDto.setHra(salary.getHra());
        salaryDto.setBenefits(salary.getBenefits());
        salaryDto.setEmpId(salary.getEmpId());
        return salaryDto;
    }
}
