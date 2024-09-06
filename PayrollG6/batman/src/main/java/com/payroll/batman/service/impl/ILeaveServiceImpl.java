package com.payroll.batman.service.impl ;

import com.payroll.batman.dto.LeaveTrackerDto;
import com.payroll.batman.entity.LeaveTrackerEntity;
import com.payroll.batman.exceptions.ResourceNotFoundException;
import com.payroll.batman.mapper.LeaveTrackerMapper;
import com.payroll.batman.repository.LeaveTrackerRepository;
import com.payroll.batman.service.ILeaveService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ILeaveServiceImpl implements ILeaveService {

    private LeaveTrackerRepository leaveTrackerRepository;
    @Override
    public LeaveTrackerDto getTracker(Long empId) {

        LeaveTrackerEntity leaveTrackerEntity = leaveTrackerRepository.findByEmpId(empId).orElseThrow(
                () -> new ResourceNotFoundException("LeaveTracker", "Employee ID", empId.toString())
        );
        LeaveTrackerDto leaveTrackerDto= LeaveTrackerMapper.mapToLeaveTrackerDto(leaveTrackerEntity, new LeaveTrackerDto());
        return leaveTrackerDto;

    }

    @Override
    public void createTracker(LeaveTrackerDto leaveTrackerDto) {
        LeaveTrackerEntity leaveTrackerEntity= LeaveTrackerMapper.mapToLeaveTracker(leaveTrackerDto, new LeaveTrackerEntity());
        leaveTrackerRepository.save(leaveTrackerEntity);
    }

    @Override
    public boolean upsertTracker(LeaveTrackerDto leaveTrackerDto) {

        Optional<LeaveTrackerEntity> leaveTrackerEntity = leaveTrackerRepository.findByEmpId(leaveTrackerDto.getEmpId());

        if(leaveTrackerEntity.isPresent())
        {
            LeaveTrackerEntity leaveTracker = LeaveTrackerMapper.mapToLeaveTracker(leaveTrackerDto, leaveTrackerEntity.get());
            leaveTrackerRepository.save(leaveTracker);
        }
        else{
            createTracker(leaveTrackerDto);
        }
        return true ;
    }

    @Override
    public void initLeavePool(Long empId) {
        LeaveTrackerDto leaveTrackerDto = new LeaveTrackerDto();
        leaveTrackerDto.setEmpId(empId);
        leaveTrackerDto.setCasualLeaves(40L);
        leaveTrackerDto.setSickLeaves(40L);
        leaveTrackerDto.setEarnedLeaves(40L);
        leaveTrackerDto.setUnpaidLeaves(0L);
        createTracker(leaveTrackerDto);
    }


}
