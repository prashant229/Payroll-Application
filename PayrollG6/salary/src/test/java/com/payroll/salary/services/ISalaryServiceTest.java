package com.payroll.salary.services;

import com.payroll.salary.dto.SalaryHistoryDto;
import com.payroll.salary.entity.SalaryEntity;
import com.payroll.salary.entity.SalaryHistoryEntity;
import com.payroll.salary.repository.SalaryHistoryRepository;
import com.payroll.salary.repository.SalaryRepository;
import com.payroll.salary.services.impl.SalaryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.List;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ISalaryServiceTest {

    @Mock
    private SalaryRepository salaryRepository;
    @Mock
    private SalaryHistoryRepository salaryHistoryRepository;

    @InjectMocks
    private SalaryServiceImpl salaryService;

    @BeforeEach
    void initialise(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getPayrollDetails() {
        LocalDate start = LocalDate.of(2023,1,1);
        LocalDate end = LocalDate.of(2023,12,31);
        SalaryHistoryEntity salaryHistoryEntity = new SalaryHistoryEntity(123L,456L,10000L,100000L,5000L,LocalDate.of(2023,6,30),85000L,2000L);
        List<SalaryHistoryEntity> salaryHistoryDtoList = List.of(salaryHistoryEntity);

        //mock external calls
        Mockito.when(salaryHistoryRepository.findAll()).thenReturn(salaryHistoryDtoList);

        //test call
        List<SalaryHistoryDto> salaryHistoryDtos=salaryService.getPayrollDetails(start,end);

        //assertions
        assertNotNull(salaryHistoryDtos);
        assertEquals(1,salaryHistoryDtos.size());
        SalaryHistoryDto salaryHistoryDto = salaryHistoryDtos.get(0);
        assertEquals(salaryHistoryEntity.getEmpId(),salaryHistoryDto.getEmpId());
        assertEquals(salaryHistoryEntity.getHra(),salaryHistoryDto.getHra());
        assertEquals(salaryHistoryEntity.getBasic(),salaryHistoryDto.getBasic());
        assertEquals(salaryHistoryEntity.getBenefits(),salaryHistoryDto.getBenefits());
        assertEquals(salaryHistoryEntity.getDateOfPayment(),salaryHistoryDto.getDateOfPayment());
        assertEquals(salaryHistoryEntity.getPaymentAmount(),salaryHistoryDto.getPaymentAmount());
        assertEquals(salaryHistoryEntity.getUnpaidLeaveDeduction(),salaryHistoryDto.getUnpaidLeaveDeduction());

        Mockito.verify(salaryHistoryRepository).findAll();
    }

    @Test
    void getEmpDetails() {
        LocalDate startDate = LocalDate.of(2023,1,1);
        LocalDate endDate = LocalDate.of(2023,12,31);
        Long empId = 678L;

        SalaryHistoryEntity salaryHistoryEntity = new SalaryHistoryEntity(245L,empId,10000L,100000L,5000L,LocalDate.of(2023,6,30),85000L,2000L);
        List<SalaryHistoryEntity> salaryHistoryEntities = List.of(salaryHistoryEntity);

        Mockito.when(salaryHistoryRepository.findByEmpId(empId)).thenReturn(salaryHistoryEntities);

        List<SalaryHistoryDto> salaryHistoryDtos = salaryService.getEmpDetails(startDate, endDate, empId);

        assertNotNull(salaryHistoryDtos);
        assertEquals(1,salaryHistoryDtos.size());
        SalaryHistoryDto salaryHistoryDto = salaryHistoryDtos.get(0);
        assertEquals(salaryHistoryEntity.getEmpId(),salaryHistoryDto.getEmpId());
        assertEquals(salaryHistoryEntity.getHra(),salaryHistoryDto.getHra());
        assertEquals(salaryHistoryEntity.getBasic(),salaryHistoryDto.getBasic());
        assertEquals(salaryHistoryEntity.getBenefits(),salaryHistoryDto.getBenefits());
        assertEquals(salaryHistoryEntity.getDateOfPayment(),salaryHistoryDto.getDateOfPayment());
        assertEquals(salaryHistoryEntity.getPaymentAmount(),salaryHistoryDto.getPaymentAmount());
        assertEquals(salaryHistoryEntity.getUnpaidLeaveDeduction(),salaryHistoryDto.getUnpaidLeaveDeduction());

        Mockito.verify(salaryHistoryRepository).findByEmpId(empId);
    }

    @Test
    void createSalaryRecord() {

    }

    @Test
    void createNewSalaryRecord() {
    }
}