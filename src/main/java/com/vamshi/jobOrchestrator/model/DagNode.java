package com.vamshi.jobOrchestrator.model;

import jakarta.persistence.*;

@Entity
public class DagNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String condition;
    @ManyToOne
    @JoinColumn(name = "dag_id")
    private Dag dag;
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    // Getters, setters, constructors
}

