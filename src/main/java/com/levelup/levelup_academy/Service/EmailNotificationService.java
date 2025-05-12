package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.EmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class EmailNotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void sendEmail(EmailRequest emailRequest) {

        if (emailRequest == null)
            throw new ApiException("email null");

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(senderEmail);
            helper.setTo(emailRequest.getRecipient());
            helper.setSubject(emailRequest.getSubject());
            helper.setSentDate(Date.from(Instant.now()));

            helper.setText(emailRequest.getMessage(), true); // 'true' means HTML

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new ApiException("Failed to send email: " + e.getMessage());
        }
    }
}
