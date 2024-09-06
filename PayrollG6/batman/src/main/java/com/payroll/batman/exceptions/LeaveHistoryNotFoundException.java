package com.payroll.batman.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class LeaveHistoryNotFoundException extends RuntimeException {
    public LeaveHistoryNotFoundException(String message) {
        super(message);
    }
}
