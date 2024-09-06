package com.payroll.salary.services.impl;

import com.payroll.salary.dto.SalaryDto;
import com.payroll.salary.dto.SalaryHistoryDto;
import com.payroll.salary.entity.SalaryEntity;
import com.payroll.salary.entity.SalaryHistoryEntity;
import com.payroll.salary.mapper.SalaryHistoryMapper;
import com.payroll.salary.mapper.SalaryMapper;
import com.payroll.salary.repository.SalaryHistoryRepository;
import com.payroll.salary.repository.SalaryRepository;
import com.payroll.salary.services.ISalaryService;
//import com.payroll.salary.services.clients.LeaveFeignClient;
import com.payroll.salary.services.clients.LeaveFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class SalaryServiceImpl implements ISalaryService {

    private SalaryHistoryRepository salaryHistoryRepositoryObj;
    private SalaryRepository salaryRepositoryObj;
    LeaveFeignClient leaveFeignClient;


    @Override
    public List<SalaryHistoryDto> getPayrollDetails(LocalDate start, LocalDate end) {
        List<SalaryHistoryEntity> salaryHistoryEntity= salaryHistoryRepositoryObj.findAll();

        List<SalaryHistoryDto> salaryHistoryDtoList = salaryHistoryEntity.stream()
                .filter(x -> !x.getDateOfPayment().isBefore(start) && !x.getDateOfPayment().isAfter(end))
                .map(x -> SalaryHistoryMapper.mapToSalaryHistoryDto(x, new SalaryHistoryDto()))
                .toList();

        return salaryHistoryDtoList;
    }

    @Override
    public List<SalaryHistoryDto> getEmpDetails(LocalDate startDate, LocalDate endDate, Long empId) {
        List<SalaryHistoryEntity> salaryEntity= salaryHistoryRepositoryObj.findByEmpId(empId);

        List<SalaryHistoryDto> salaryHistoryDtoList = salaryEntity.stream()
                .filter(x -> !x.getDateOfPayment().isBefore(startDate) && !x.getDateOfPayment().isAfter(endDate))
                .map(x -> SalaryHistoryMapper.mapToSalaryHistoryDto(x, new SalaryHistoryDto()))
                .toList();

        return salaryHistoryDtoList;
    }

    @Override
    public void createSalaryRecord(LocalDate dateOfPayment){
        List<SalaryEntity> salaryRecord= salaryRepositoryObj.findAll();
        for(SalaryEntity salary : salaryRecord) {
            Long leaveDeduction = getUnpaidLeaveDeductions(salary, dateOfPayment);
            SalaryHistoryEntity salaryHistory = new SalaryHistoryEntity();
            salaryHistory.setEmpId(salary.getEmpId());
            salaryHistory.setHra(salary.getHra());
            salaryHistory.setBasic(salary.getBasic());
            salaryHistory.setBenefits(salary.getBenefits());
            salaryHistory.setDateOfPayment(dateOfPayment);
            salaryHistory.setUnpaidLeaveDeduction(leaveDeduction);
            salaryHistory.setPaymentAmount(getPaymentAmount(salary, leaveDeduction));
            salaryHistoryRepositoryObj.save(salaryHistory);
        }
    }

    @Override
    public void createNewSalaryRecord(SalaryDto salaryDto) {
        SalaryEntity salary= SalaryMapper.mapToSalary(salaryDto, new SalaryEntity());
        salaryRepositoryObj.save(salary);
    }

    public Long getLeaveDeduction(SalaryEntity salary,Long noOfLeaves){
        return salary.getBasic()/30*noOfLeaves;
    }

    public Long getPaymentAmount(SalaryEntity salary, Long leaveDeduction){
        return salary.getBasic() + salary.getBenefits() + salary.getHra() - leaveDeduction;
    }

    private Long getUnpaidLeaves(Long empId, LocalDate from, LocalDate to){
        return leaveFeignClient.getUnpaidLeaves(empId, from, to).getBody();
    }

    private Long getUnpaidLeaveDeductions(SalaryEntity salary, LocalDate dateOfPayment){
        Long empId = salary.getEmpId() ;
        LocalDate dateOfPaymentMinus30Days = dateOfPayment.minusDays(30);
        return salary.getBasic()/30*getUnpaidLeaves(empId, dateOfPaymentMinus30Days, dateOfPayment);
    }
}

