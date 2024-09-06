package com.payroll.batman.exceptions;

import com.payroll.batman.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                ex.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidDateRangeException(InvalidDateRangeException ex, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                ex.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectStatusException.class)
    public ResponseEntity<ErrorResponseDto> handleIncorrectStatusException(IncorrectStatusException ex, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                ex.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectLeaveTypeException.class)
    public ResponseEntity<ErrorResponseDto> handleIncorrectStatusException(IncorrectLeaveTypeException ex, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                ex.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientLeaveBalanceException.class)
    public ResponseEntity<ErrorResponseDto> handleInsufficientLeaveBalanceException(InsufficientLeaveBalanceException ex, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                ex.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEmployeeNotFoundException(EmployeeNotFoundException ex, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                ex.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(LeaveHistoryNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleLeaveHistoryNotFoundException(LeaveHistoryNotFoundException ex, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                ex.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }



}
