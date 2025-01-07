package com.example.automation_service.repository;

import com.example.automation_service.model.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface WorkflowRepository extends JpaRepository<Workflow, UUID> {
    List<Workflow> findByStatus(String status);
}