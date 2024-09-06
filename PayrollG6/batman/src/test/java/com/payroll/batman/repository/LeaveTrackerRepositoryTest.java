package com.payroll.batman.repository;

import com.payroll.batman.dto.LeaveTrackerDto;
import com.payroll.batman.entity.LeaveTrackerEntity;
//import org.assertj.core.api.AbstractFileAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LeaveTrackerRepositoryTest {
    @Autowired
    private LeaveTrackerRepository leaveTrackerRepository;

    @Test
    void findByEmpId() {
        LeaveTrackerEntity leaveTrackerEntity= new LeaveTrackerEntity(1L,1234567L,10L,10L,10L,1L);
        leaveTrackerRepository.save(leaveTrackerEntity);

        Optional<LeaveTrackerEntity> actualResult = leaveTrackerRepository.findByEmpId(1234567L);
        assertTrue(actualResult.isPresent());

    }

}