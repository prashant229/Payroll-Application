package com.payroll.message.service.impl;

import com.payroll.message.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.properties.domain_name}")
    private String from;

    @Override
    public void sendEmail(String to, String subject, String body) {
        // send email

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(from);
        emailSender.send(message);

    }

    @Override
    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(htmlContent, true); // true indicates HTML content
            messageHelper.setFrom(from);
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle exception as needed
        }
    }
}
