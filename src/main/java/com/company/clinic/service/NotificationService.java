package com.company.clinic.service;

import com.company.clinic.model.visit.Visit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
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

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

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
        String emailMessage = buildResetMail(token);
        sendMail(email, "Password reset", emailMessage);
    }

    @Async
    public void sendMailForVisitCreation(String email, Visit visit) {
        String emailMessage =
                String.format("Visit on %s at Doctor %s has been set up",
                        visit.getVisitTime().toString(), visit.getDoctor().getFirstName() + visit.getDoctor().getLastName());
        sendMail(email, subject, emailMessage);
    }

    @Async
    public void sendMailForVisitConfirmation(String email, Visit visit) {
        String emailMessage =
                String.format("Visit on %s at Doctor %s was confirmed",
                        visit.getVisitTime().toString(), visit.getDoctor().getFirstName() + visit.getDoctor().getLastName());
        sendMail(email, subject, emailMessage);
    }

    @Async
    public void sendMailForVisitCancellation(String email, Visit visit) {
        String emailMessage =
                String.format("Visit on %s at Doctor %s was canceled",
                        visit.getVisitTime().toString(), visit.getDoctor().getFirstName() + visit.getDoctor().getLastName());
        sendMail(email, subject, emailMessage);
    }

    public void sendMail(String emailTo, String emailSubject, String emailMessage) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(emailTo);
            helper.setSubject(emailSubject);
            helper.setText(emailMessage);
            javaMailSender.send(message);
        } catch (MessagingException exc) {
            logger.error("Error encountered during email sending ", exc);
        } catch (MailSendException exc) {
            logger.error("Error encountered during email sending. Trying to send it one more time!", exc);
            javaMailSender.send(message); // when a mail sending error is encountered try to send email one more time
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