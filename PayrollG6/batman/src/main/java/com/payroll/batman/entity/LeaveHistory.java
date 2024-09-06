package com.payroll.batman.entity;


import com.payroll.batman.enums.LeaveStatus;
import com.payroll.batman.enums.LeaveType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "LEAVE_HISTORY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveId ;
    private Long empId ;
    private LeaveType leaveType ;
    private Long mangerId ;
    private LocalDate startDate;
    private LocalDate endDate ;
    private String reason ;
    private LeaveStatus status ;
    private String rejectionReason ;
    private Long noOfLeaves;
    private String leaveToken ;
}
