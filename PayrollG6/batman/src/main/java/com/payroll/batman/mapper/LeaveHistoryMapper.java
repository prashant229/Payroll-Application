package com.payroll.batman.mapper;

import com.payroll.batman.dto.LeaveHistoryDto;
import com.payroll.batman.entity.LeaveHistory;

public class LeaveHistoryMapper {

    public static LeaveHistory toLeaveHistory(LeaveHistoryDto leaveHistoryDto , LeaveHistory leaveHistory){
        leaveHistory.setLeaveType(leaveHistoryDto.getLeaveType());
        leaveHistory.setEndDate(leaveHistoryDto.getEndDate());
        leaveHistory.setStartDate(leaveHistoryDto.getStartDate());
        leaveHistory.setReason(leaveHistoryDto.getReason());
        leaveHistory.setStatus(leaveHistoryDto.getStatus());
        leaveHistory.setEmpId(leaveHistoryDto.getEmpId());
        leaveHistory.setRejectionReason(leaveHistoryDto.getRejectionReason());
        leaveHistory.setNoOfLeaves(leaveHistoryDto.getNoOfLeaves());
        leaveHistory.setMangerId(leaveHistoryDto.getMangerId());
        return leaveHistory ;
    }

    public static LeaveHistoryDto toLeaveHistoryDto(LeaveHistory leaveHistory ,  LeaveHistoryDto leaveHistoryDto){
        leaveHistoryDto.setLeaveType(leaveHistory.getLeaveType());
        leaveHistoryDto.setEndDate(leaveHistory.getEndDate());
        leaveHistoryDto.setStartDate(leaveHistory.getStartDate());
        leaveHistoryDto.setReason(leaveHistory.getReason());
        leaveHistoryDto.setStatus(leaveHistory.getStatus());
        leaveHistoryDto.setEmpId(leaveHistory.getEmpId());
        leaveHistoryDto.setRejectionReason(leaveHistory.getRejectionReason());
        leaveHistoryDto.setNoOfLeaves(leaveHistory.getNoOfLeaves());
        leaveHistoryDto.setMangerId(leaveHistory.getMangerId());
        return leaveHistoryDto ;
    }
}
