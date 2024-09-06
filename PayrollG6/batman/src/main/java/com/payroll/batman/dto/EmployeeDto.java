package com.payroll.batman.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    @NotNull(message = "Employee ID is mandatory")
    @Positive(message = "Employee ID must be a positive number")
    private long employeeId;

    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String firstName;
    private String lastName;
    private String role;
    private String gender;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String emailAddress;
    @NotNull(message = "Employee ID is mandatory")
    @Positive(message = "Employee ID must be a positive number")
    private long managerId;

    @Size(min = 10, max = 15, message = "Contact number must be between 10 and 15 digits")
    private String contactNumber;

    @NotNull(message = "Date of birth is mandatory")
    @Past(message = "Date of birth must be a past date")
    private Date dob;
}
