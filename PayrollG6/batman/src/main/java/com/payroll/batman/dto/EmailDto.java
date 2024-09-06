package com.payroll.batman.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class EmailDto {
    private String email;
    private String emailSubject;
    private String emailBody;

}
