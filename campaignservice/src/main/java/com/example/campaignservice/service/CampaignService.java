package com.example.campaignservice.service;

import com.example.campaignservice.model.Campaign;
import com.example.campaignservice.model.Subscriber;
import com.example.campaignservice.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private RestTemplate restTemplate;


    public Campaign createCampaign(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    public Campaign getCampaignById(Long id) {
        return campaignRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Campaign not found with id: " + id));
    }

    public List<Subscriber> getSubscribersForCampaign(Long campaignId) {
        String url = "http://localhost:8082/api/subscribers";
        ResponseEntity<List<Subscriber>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Subscriber>>() {}
        );
        return response.getBody();
    }

    public void triggerCampaignSending(Long campaignId) {
        // Fetch campaign details
        Campaign campaign = getCampaignById(campaignId);

        // Get subscribers
        List<Subscriber> subscribers = getSubscribersForCampaign(campaignId);

        // Send email to each subscriber
        for (Subscriber subscriber : subscribers) {
            sendEmailToSubscriber(subscriber, campaign);
        }
    }
    private void sendEmailToSubscriber(Subscriber subscriber, Campaign campaign) {
        String url = "http://localhost:8083/api/v1/emails/send";

        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
        bodyParams.add("to", subscriber.getEmail());
        bodyParams.add("subject", campaign.getName());
        bodyParams.add("body", campaign.getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Prepare the HTTP entity with body and headers
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(bodyParams, headers);

        // Make the POST request
        restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }


    public Campaign updateCampaign(Long id, Campaign updatedCampaign) {
        Campaign existingCampaign = getCampaignById(id);
        existingCampaign.setName(updatedCampaign.getName());
        existingCampaign.setType(updatedCampaign.getType());
        existingCampaign.setStartDate(updatedCampaign.getStartDate());
        existingCampaign.setEndDate(updatedCampaign.getEndDate());
        existingCampaign.setStatus(updatedCampaign.getStatus());
        existingCampaign.setContent(updatedCampaign.getContent());
        return campaignRepository.save(existingCampaign);
    }

    public void deleteCampaign(Long id) {
        campaignRepository.deleteById(id);
    }
}
