package com.automationservice.client;

import com.automationservice.model.Workflow;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("sending-service")
public interface SendingServiceClient {
    @PostMapping("/sendEmail")
    void sendEmail(@RequestBody Workflow workflow);
}