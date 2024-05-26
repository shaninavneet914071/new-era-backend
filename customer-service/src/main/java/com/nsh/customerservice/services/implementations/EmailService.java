package com.nsh.customerservice.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("shaninavneet914071@gmail.com");
        message.setTo("shaniraj914071@gmail.com");
        message.setSubject("Test mail");
        message.setText("test body");
        mailSender.send(message);
        System.out.println("Message sent successfully");
    }
}