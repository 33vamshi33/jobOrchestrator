package com.vamshi.jobOrchestrator.persistence;

import com.vamshi.jobOrchestrator.model.JobExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobExecutionRepository extends JpaRepository<JobExecution, Long> {
}

