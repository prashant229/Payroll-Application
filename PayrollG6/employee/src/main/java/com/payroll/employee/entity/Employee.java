package com.payroll.employee.entity;

import com.payroll.employee.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "employee")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Employee extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @NotNull(message = "Employee first name can not be empty")
    private String firstName;
    private String lastName;
    private UserRole role;
    private String gender;
    @NotNull
    @Email
    private String emailAddress;
    private String password;
    private Long managerId;
    @NotNull
    @Pattern(regexp = "^$|[0-9]{10}", message = "Contact number should have 10 digits")
    private String contactNumber;
    @Past(message = "Date of birth should be in the past")
    private Date dob;

}
