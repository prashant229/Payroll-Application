package com.payroll.batman.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InsufficientLeaveBalanceException extends RuntimeException{
    public InsufficientLeaveBalanceException(String message){
        super(message);
    }
}
