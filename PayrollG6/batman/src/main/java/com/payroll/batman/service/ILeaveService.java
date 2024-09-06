package com.payroll.batman.service;

import com.payroll.batman.dto.LeaveTrackerDto;

public interface ILeaveService {
   LeaveTrackerDto getTracker(Long empId);
   void createTracker(LeaveTrackerDto leaveTrackerDto);

   boolean upsertTracker(LeaveTrackerDto leaveTrackerDto) ;

   void initLeavePool(Long empId);
}
