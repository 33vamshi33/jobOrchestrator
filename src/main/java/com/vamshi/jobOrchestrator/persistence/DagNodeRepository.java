package com.vamshi.jobOrchestrator.persistence;

import com.vamshi.jobOrchestrator.model.DagNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DagNodeRepository extends JpaRepository<DagNode, Long> {
}

