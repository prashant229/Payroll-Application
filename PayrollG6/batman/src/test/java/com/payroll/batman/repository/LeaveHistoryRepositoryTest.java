package com.payroll.batman.repository;

import com.payroll.batman.entity.LeaveHistory;
import com.payroll.batman.enums.LeaveStatus;
import com.payroll.batman.enums.LeaveType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LeaveHistoryRepositoryTest {
    @Autowired
    private LeaveHistoryRepository leaveHistoryRepository;

    @Test
    void getLeaveHistoryByDateAndEmpId() {
        LeaveType type = LeaveType.SICK_LEAVE;
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LeaveStatus status = LeaveStatus.PENDING_FROM_MANAGER;
        LeaveHistory leaveHistory= new LeaveHistory(1L,12345678L,type,today,tomorrow,"Sick",status,"No reason",2L);

        List<LeaveHistory> leaveList= new ArrayList<>();
        leaveList.add(leaveHistory);

        leaveHistoryRepository.save(leaveHistory);

        Optional<List<LeaveHistory>> actualResult= leaveHistoryRepository.getLeaveHistoryByDateAndEmpId(12345678L, today, tomorrow);
        //assertTrue(actualResult.isPresent());
        assertEquals(actualResult.get(),leaveList);




    }

    @Test
    void findByEmpId() {
        LeaveType type = LeaveType.SICK_LEAVE;
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LeaveStatus status = LeaveStatus.PENDING_FROM_MANAGER;
        LeaveHistory leaveHistory= new LeaveHistory(1L,12345678L,type,today,tomorrow,"Sick",status,"No reason",2L);

        leaveHistoryRepository.save(leaveHistory);

        Optional<List<LeaveHistory>> actualResult = leaveHistoryRepository.findByEmpId(12345678L);
        assertTrue(actualResult.isPresent());

    }

}