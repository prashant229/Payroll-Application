package com.payroll.employee.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {

    private String errorMessage;
    private String apiPath;
    private HttpStatus statusCode;
    private LocalDateTime timestamp;
}
