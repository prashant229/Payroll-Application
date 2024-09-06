package com.payroll.salary.mapper;

import com.payroll.salary.dto.SalaryHistoryDto;
import com.payroll.salary.entity.SalaryHistoryEntity;

public class SalaryHistoryMapper {

    public static SalaryHistoryEntity mapToSalaryHistory(SalaryHistoryDto salaryHistoryDto, SalaryHistoryEntity salaryHistory){
        salaryHistory.setBasic(salaryHistoryDto.getBasic());
        salaryHistory.setHra(salaryHistoryDto.getHra());
        salaryHistory.setBenefits(salaryHistoryDto.getBenefits());
        salaryHistory.setEmpId(salaryHistoryDto.getEmpId());
        salaryHistory.setPaymentAmount(salaryHistoryDto.getPaymentAmount());
        salaryHistory.setDateOfPayment(salaryHistoryDto.getDateOfPayment());
        salaryHistory.setUnpaidLeaveDeduction(salaryHistoryDto.getUnpaidLeaveDeduction());
        return salaryHistory;
    }

    public static SalaryHistoryDto mapToSalaryHistoryDto(SalaryHistoryEntity salaryHistory, SalaryHistoryDto salaryHistoryDto) {
        salaryHistoryDto.setBasic(salaryHistory.getBasic());
        salaryHistoryDto.setHra(salaryHistory.getHra());
        salaryHistoryDto.setBenefits(salaryHistory.getBenefits());
        salaryHistoryDto.setEmpId(salaryHistory.getEmpId());
        salaryHistoryDto.setPaymentAmount(salaryHistory.getPaymentAmount());
        salaryHistoryDto.setDateOfPayment(salaryHistory.getDateOfPayment());
        salaryHistoryDto.setUnpaidLeaveDeduction(salaryHistory.getUnpaidLeaveDeduction());
        return salaryHistoryDto;
    }
}

//    public static List<SalaryDto> mapToSalaryDtoList(List<Salary> salaryList, List<SalaryDto> salaryDtoList){
//        List<SalaryDto> SalaryDtoList = new ArrayList<>();
//        for (Salary salaryObj : salaryList) {
//            SalaryDto salaryDtoObj =  mapToSalaryHistoryDto(salaryObj, new SalaryDto());
//            SalaryDtoList.add(salaryDtoObj);
//        }
//        return SalaryDtoList;
//    }
