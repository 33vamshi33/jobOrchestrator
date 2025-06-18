package com.vamshi.jobOrchestrator.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String type; // e.g., shell, http, etc.
    private String payload;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<JobExecution> executions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dependency_id")
    private Job dependency; // The job that must run before this job

    @Enumerated(EnumType.STRING)
    private JobState state = JobState.WAITING;

    @Enumerated(EnumType.STRING)
    private JobAction action = JobAction.NONE;

    private String submittedBy;
    private String lastModifiedBy;
    private String lastAction;
    private LocalDateTime lastActionTime;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> hostPool;
    private String assignedHost;

    // Getters, setters, constructors
}
