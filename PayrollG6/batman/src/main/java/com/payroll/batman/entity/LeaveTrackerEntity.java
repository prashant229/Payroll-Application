package com.payroll.batman.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "LEAVE_TRACKER")
@Data
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class LeaveTrackerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveTrackerId;
    private Long empId;
    private Long casualLeaves;
    private Long sickLeaves;
    private Long earnedLeaves;
    private Long unpaidLeaves;
}
