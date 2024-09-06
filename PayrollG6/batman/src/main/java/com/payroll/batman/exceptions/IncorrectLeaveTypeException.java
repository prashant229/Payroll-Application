package com.payroll.batman.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IncorrectLeaveTypeException extends RuntimeException{
    public IncorrectLeaveTypeException(String message){
        super(message);
    }

}
