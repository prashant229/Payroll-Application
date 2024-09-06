package com.payroll.batman.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidDateRangeException extends RuntimeException{

    public InvalidDateRangeException(String message){
        super(message) ;
    }
}
