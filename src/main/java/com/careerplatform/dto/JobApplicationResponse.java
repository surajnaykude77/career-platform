package com.careerplatform.dto;

import com.careerplatform.entity.ApplicationStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record JobApplicationResponse(
        Long id,
        String companyName,
        String jobTitle,
        String jobDescription,
        String location,
        LocalDate applicationDate,
        String jobUrl,
        String salaryRange,
        ApplicationStatus status,
        String notes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}