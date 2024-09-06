package com.payroll.batman.mapper;

import com.payroll.batman.dto.LeaveTrackerDto;
import com.payroll.batman.entity.LeaveTrackerEntity;

public class LeaveTrackerMapper {
    public static LeaveTrackerEntity mapToLeaveTracker(LeaveTrackerDto leaveTrackerDto, LeaveTrackerEntity leaveTrackerEntity){

        leaveTrackerEntity.setUnpaidLeaves(leaveTrackerDto.getUnpaidLeaves());
        leaveTrackerEntity.setSickLeaves(leaveTrackerDto.getSickLeaves());
        leaveTrackerEntity.setCasualLeaves(leaveTrackerDto.getCasualLeaves());
        leaveTrackerEntity.setEarnedLeaves(leaveTrackerDto.getEarnedLeaves());
        leaveTrackerEntity.setEmpId(leaveTrackerDto.getEmpId());

        return leaveTrackerEntity;
    }
    public static LeaveTrackerDto mapToLeaveTrackerDto(LeaveTrackerEntity leaveTrackerEntity, LeaveTrackerDto leaveTrackerDto){

        leaveTrackerDto.setSickLeaves(leaveTrackerEntity.getSickLeaves());
        leaveTrackerDto.setCasualLeaves(leaveTrackerEntity.getCasualLeaves());
        leaveTrackerDto.setUnpaidLeaves(leaveTrackerEntity.getUnpaidLeaves());
        leaveTrackerDto.setEarnedLeaves(leaveTrackerEntity.getEarnedLeaves());
        leaveTrackerDto.setEmpId(leaveTrackerEntity.getEmpId());

        return leaveTrackerDto;
    }
}
