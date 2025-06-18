package com.vamshi.jobOrchestrator.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class JobExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;
    private String status; // e.g., PENDING, RUNNING, SUCCESS, FAILED
    private String logs;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private String triggeredBy;
    private String action;

    // Getters, setters, constructors


}
