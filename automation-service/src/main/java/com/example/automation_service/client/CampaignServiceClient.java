package com.example.automation_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "campaignservice")
public interface CampaignServiceClient {

    @PostMapping("/api/campaigns/{id}/send")
    void triggerCampaignSending(@PathVariable("id") Long campaignId);
}