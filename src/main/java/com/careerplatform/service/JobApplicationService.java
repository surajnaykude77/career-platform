package com.careerplatform.service;

import com.careerplatform.exception.ResourceNotFoundException;
import com.careerplatform.dto.JobApplicationRequest;
import com.careerplatform.dto.JobApplicationResponse;
import com.careerplatform.entity.ApplicationStatus;
import com.careerplatform.entity.JobApplication;
import com.careerplatform.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    private final JobApplicationRepository repository;

    public JobApplicationService(JobApplicationRepository repository) {
        this.repository = repository;
    }

    public JobApplicationResponse createApplication(JobApplicationRequest request) {
        JobApplication application = new JobApplication();
        mapRequestToEntity(request, application);

        if (application.getStatus() == null) {
            application.setStatus(ApplicationStatus.APPLIED);
        }

        JobApplication saved = repository.save(application);
        return mapEntityToResponse(saved);
    }

    public List<JobApplicationResponse> getAllApplications() {
        return repository.findAll()
                .stream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    public JobApplicationResponse getApplicationById(Long id) {
        JobApplication application = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found with id: " + id));
        return mapEntityToResponse(application);
    }

    public JobApplicationResponse updateApplication(Long id, JobApplicationRequest request) {
        JobApplication existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found with id: " + id));

        mapRequestToEntity(request, existing);
        JobApplication updated = repository.save(existing);
        return mapEntityToResponse(updated);
    }

    public void deleteApplication(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Job application not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private void mapRequestToEntity(JobApplicationRequest request, JobApplication entity) {
        entity.setCompanyName(request.companyName());
        entity.setJobTitle(request.jobTitle());
        entity.setJobDescription(request.jobDescription());
        entity.setLocation(request.location());
        entity.setApplicationDate(request.applicationDate());
        entity.setJobUrl(request.jobUrl());
        entity.setSalaryRange(request.salaryRange());
        if (request.status() != null) {
            entity.setStatus(request.status());
        }
        entity.setNotes(request.notes());
    }

    private JobApplicationResponse mapEntityToResponse(JobApplication entity) {
        return new JobApplicationResponse(
                entity.getId(),
                entity.getCompanyName(),
                entity.getJobTitle(),
                entity.getJobDescription(),
                entity.getLocation(),
                entity.getApplicationDate(),
                entity.getJobUrl(),
                entity.getSalaryRange(),
                entity.getStatus(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}