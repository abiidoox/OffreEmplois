package com.springproject.offre_emploi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private  final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public boolean sendEmail(String from, String subject, String text) {
        // ...
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject(subject);
        msg.setText(text);
        msg.setFrom(from);
        msg.setTo("HAZMIRI.younes@ensam-casa.ma");
        javaMailSender.send(msg);
        return true;
    }

}
