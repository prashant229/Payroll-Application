package com.payroll.batman.service;

import com.payroll.batman.dto.LeaveHistoryDto;

import java.time.LocalDate;
import java.util.List;

public interface ILeaveHistoryService {

    List<LeaveHistoryDto> getLeaveHistoryByEmpID(Long EmpId);

    List<LeaveHistoryDto> getLeaveHistoryByEmpIdAndDate(Long empId , LocalDate from , LocalDate to);

    boolean processLeaveRequest(LeaveHistoryDto leaveHistoryDto);

    boolean createLeaveHistoryRecord(LeaveHistoryDto leaveHistoryDto);

    Long getUnpaidLeaves(Long empId, LocalDate from, LocalDate to);

    List<LeaveHistoryDto> getLeavesForManagerApproval(Long managerId);

    boolean takeAction(Long leaveId, String newStatus, String rejectionReason);

    boolean deleteLeaveHistory(Long leaveId);

    boolean approveLeave(String leaveToken);

    boolean rejectLeave(String leaveToken);
}
