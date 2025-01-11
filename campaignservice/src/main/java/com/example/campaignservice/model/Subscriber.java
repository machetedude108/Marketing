package com.example.campaignservice.model;
import lombok.Data;

@Data
public class Subscriber {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}