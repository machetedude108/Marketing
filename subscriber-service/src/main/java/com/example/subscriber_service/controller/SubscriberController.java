package com.example.subscriber_service.controller;

import com.example.subscriber_service.model.Subscriber;
import com.example.subscriber_service.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscribers")
@RequiredArgsConstructor
public class SubscriberController {
    private final SubscriberService subscriberService;

    @GetMapping
    public List<Subscriber> getAllSubscribers() {
        return subscriberService.getAllSubscribers();
    }

    @GetMapping("/{id}")
    public Optional<Subscriber> getSubscriberById(@PathVariable Long id) {
        return subscriberService.getSubscriberById(id);
    }

    @PostMapping
    public Subscriber createSubscriber(@RequestBody Subscriber subscriber) {
        return subscriberService.createSubscriber(subscriber);
    }

    @PutMapping("/{id}")
    public Subscriber updateSubscriber(@PathVariable Long id, @RequestBody Subscriber subscriberDetails) {
        return subscriberService.updateSubscriber(id, subscriberDetails);
    }

    @DeleteMapping("/{id}")
    public boolean deleteSubscriber(@PathVariable Long id) {
        return subscriberService.deleteSubscriber(id);
    }
}
