package com.payroll.batman.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
public class ErrorResponseDto {
    private String errorMessage;
    private String apiPath;
    private HttpStatus statusCode;
    private LocalDateTime timestamp;
}
