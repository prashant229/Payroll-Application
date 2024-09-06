package com.payroll.batman.functions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class LeaveFunction {

    @Bean
    public Consumer<Long> updateCommunication(){
        return (leaveId) -> {
            System.out.println("Leave Id : "+ leaveId + " is updated");
        };
    }
}
