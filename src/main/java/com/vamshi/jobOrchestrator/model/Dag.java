package com.vamshi.jobOrchestrator.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Dag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "dag", cascade = CascadeType.ALL)
    private List<DagNode> nodes;

    // Getters, setters, constructors
}

