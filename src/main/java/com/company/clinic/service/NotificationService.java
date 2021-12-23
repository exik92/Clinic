package com.company.clinic.service;

import com.company.clinic.exception.EmailSendException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class NotificationService {

    @Value(value = "${spring.mail.username}")
    private String sender;

    @Value(value = "${mail.subject}")
    private String subject;

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public NotificationService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendMailForPasswordReset(String token, String email) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(email);
            helper.setSubject(subject);
            message.setContent(buildResetMail(token), "text/html");
            javaMailSender.send(message);
        } catch (MessagingException exception) {
            throw new EmailSendException("Failed to send an email");
        }
    }


    private String buildResetMail(String token) {
        Context context = new Context(Locale.ENGLISH, getEmailVariablesAsMap(token));
        return templateEngine.process("visitInformation", context);
    }

    private Map<String, Object> getEmailVariablesAsMap(String token) {
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        return result;
    }
}