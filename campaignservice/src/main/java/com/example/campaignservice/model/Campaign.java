package com.example.campaignservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type; // "Email", "SMS", "Push Notification"

    private LocalDate startDate;

    private LocalDate endDate;

    private String status; // "Draft", "Scheduled", "Ongoing", "Completed"

    @Lob
    private String content;

    // Getters and Setters
    // Constructors
    // toString() method
}
