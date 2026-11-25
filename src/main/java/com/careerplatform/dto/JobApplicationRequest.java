package com.careerplatform.dto;

import com.careerplatform.entity.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record JobApplicationRequest(
        @NotBlank(message = "Company name is required")
        String companyName,

        @NotBlank(message = "Job title is required")
        String jobTitle,

        String jobDescription,

        String location,

        @NotNull(message = "Application date is required")
        LocalDate applicationDate,

        String jobUrl,

        String salaryRange,

        ApplicationStatus status,

        String notes
) {}