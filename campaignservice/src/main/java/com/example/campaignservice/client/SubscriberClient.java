package com.example.campaignservice.client;

import com.example.campaignservice.model.Subscriber;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "subscriber-service", url = "http://localhost:8082")
public interface SubscriberClient {
    @GetMapping("/api/subscribers/{id}")
    Subscriber getSubscriberById(@PathVariable("id") Long id);

    @GetMapping("/api/subscribers")
    List<Subscriber> getAllSubscribers();
}
