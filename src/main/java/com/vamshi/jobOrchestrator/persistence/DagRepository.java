package com.vamshi.jobOrchestrator.persistence;

import com.vamshi.jobOrchestrator.model.Dag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DagRepository extends JpaRepository<Dag, Long> {
}

