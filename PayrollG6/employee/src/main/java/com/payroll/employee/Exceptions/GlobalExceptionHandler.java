package com.payroll.employee.Exceptions;

import com.payroll.employee.dto.ErrorResponseDto;
import com.payroll.employee.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleEmployeeAlreadyExistsException(EmployeeAlreadyExistsException ex, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                ex.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.ALREADY_REPORTED,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDto,HttpStatus.ALREADY_REPORTED);
    }

    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex,WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                ex.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.ALREADY_REPORTED,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDto,HttpStatus.ALREADY_REPORTED);
    }
}
