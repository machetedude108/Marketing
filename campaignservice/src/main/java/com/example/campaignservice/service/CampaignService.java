package com.example.campaignservice.service;

import com.example.campaignservice.model.Campaign;
import com.example.campaignservice.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

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
