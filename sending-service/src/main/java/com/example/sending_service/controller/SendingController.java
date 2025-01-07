package com.example.sending_service.controller;

import com.example.sending_service.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/emails")
public class SendingController {
    private final MailService mailService;

    // Constructor injection
    public SendingController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public String sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body) {
        mailService.sendEmail(to, subject, body);
        return "Email sent successfully!";
    }

}
