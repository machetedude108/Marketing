package com.example.subscriber_service.service;

import com.example.subscriber_service.model.Subscriber;
import com.example.subscriber_service.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;

    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    public Optional<Subscriber> getSubscriberById(Long id) {
        return subscriberRepository.findById(id);
    }

    public Subscriber createSubscriber(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }

    public Subscriber updateSubscriber(Long id, Subscriber subscriberDetails) {
        Optional<Subscriber> subscriber = subscriberRepository.findById(id);
        if (subscriber.isPresent()) {
            Subscriber existingSubscriber = subscriber.get();
            existingSubscriber.setEmail(subscriberDetails.getEmail());
            existingSubscriber.setFirstName(subscriberDetails.getFirstName());
            existingSubscriber.setLastName(subscriberDetails.getLastName());
            return subscriberRepository.save(existingSubscriber);
        }
        return null;
    }

    public boolean deleteSubscriber(Long id) {
        Optional<Subscriber> subscriber = subscriberRepository.findById(id);
        if (subscriber.isPresent()) {
            subscriberRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
