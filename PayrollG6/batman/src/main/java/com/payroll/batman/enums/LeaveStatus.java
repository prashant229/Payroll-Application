package com.payroll.batman.enums;

public enum LeaveStatus {
    PENDING_FROM_MANAGER("Pending from Manager") ,
    LEAVE_APPROVED("Approved"),
    LEAVE_REJECTED("Rejected"),;

    private final String description;

    LeaveStatus(String description){
        this.description = description;
    }
    @Override
    public String toString() {
        return description;
    }
}
