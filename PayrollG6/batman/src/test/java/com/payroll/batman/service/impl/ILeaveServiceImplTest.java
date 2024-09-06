package com.payroll.batman.service.impl;

import com.payroll.batman.dto.LeaveTrackerDto;
import com.payroll.batman.entity.LeaveTrackerEntity;
import com.payroll.batman.mapper.LeaveTrackerMapper;
import com.payroll.batman.repository.LeaveTrackerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ILeaveServiceImplTest {
    @Mock
    private LeaveTrackerRepository leaveTrackerRepository;

    private ILeaveServiceImpl iLeaveService;

    @BeforeEach
    void setUp(){
        this.iLeaveService=new ILeaveServiceImpl(this.leaveTrackerRepository);
    }

    @Test
    void getTracker() {
        Long empId =12345678L;
        LeaveTrackerEntity leaveTrackerEntity= new LeaveTrackerEntity(1L,12345678L,10L,10L,10L,0L);

        LeaveTrackerDto leaveTrackerDto= LeaveTrackerMapper.mapToLeaveTrackerDto(leaveTrackerEntity, new LeaveTrackerDto());
        when(leaveTrackerRepository.findByEmpId(empId)).thenReturn(Optional.of(leaveTrackerEntity));

        iLeaveService.getTracker(empId);
        verify(leaveTrackerRepository).findByEmpId(empId);
    }

    @Test
    void createTracker() {
        LeaveTrackerDto leaveTrackerDto= new LeaveTrackerDto(12345678L,10L,10L,10L,0L);
        LeaveTrackerEntity leaveTrackerEntity= LeaveTrackerMapper.mapToLeaveTracker(leaveTrackerDto, new LeaveTrackerEntity());
        //testing
        iLeaveService.createTracker(leaveTrackerDto);

        verify(leaveTrackerRepository).save(leaveTrackerEntity);
    }

    @Test
    void upsertTracker() {
        LeaveTrackerDto leaveTrackerDto= new LeaveTrackerDto(12345678L,10L,10L,10L,0L);
        LeaveTrackerEntity leaveTrackerEntity= LeaveTrackerMapper.mapToLeaveTracker(leaveTrackerDto, new LeaveTrackerEntity());

        when(leaveTrackerRepository.findByEmpId(leaveTrackerDto.getEmpId())).thenReturn(Optional.of(leaveTrackerEntity));
        iLeaveService.upsertTracker(leaveTrackerDto);
        verify(leaveTrackerRepository).save(leaveTrackerEntity);

    }
}