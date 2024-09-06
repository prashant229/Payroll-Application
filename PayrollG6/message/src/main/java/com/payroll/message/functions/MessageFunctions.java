package com.payroll.message.functions;

import com.payroll.message.dto.EmailDto;
import com.payroll.message.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    @Autowired
    EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<EmailDto, EmailDto> email(){
        return emailDto -> {

            emailService.sendHtmlEmail("akshat.bhardwaj@ukg.com", "Leave Application", emailDto.getEmailBody());
            logger.info("Sending EMAIL to emailId :  {}" , emailDto.getEmail());
            return emailDto;
        };
    }

//    @Bean
//    public Function<LeaveMessageDto, String> sms(){
//        return leaveMessageDto -> {
//            logger.info("Sending SMS with details " + leaveMessageDto.getReason());
//            return leaveMessageDto.getManagerEmail();
//        };
//    }
}
