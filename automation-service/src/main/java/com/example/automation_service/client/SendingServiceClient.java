package com.example.automation_service.client;

import com.example.automation_service.model.Workflow;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient("sending-service")
public interface SendingServiceClient {
    @PostMapping("/api/v1/emails/send")
    void sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body
    );
}