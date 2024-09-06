package com.payroll.salary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends  RuntimeException{
    public EmployeeNotFoundException(String resource, String fieldName, Long fieldValue){
        super(String.format("%s not found for %s - %s ", resource, fieldName, fieldValue));
    }
}