package com.vamshi.jobOrchestrator.engine;

import com.vamshi.jobOrchestrator.model.Job;
import com.vamshi.jobOrchestrator.model.JobAction;
import com.vamshi.jobOrchestrator.model.JobExecution;
import com.vamshi.jobOrchestrator.model.JobState;
import com.vamshi.jobOrchestrator.persistence.JobExecutionRepository;
import com.vamshi.jobOrchestrator.persistence.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
// TODO: Check For Bugs
@Service
public class JobService {
    private final JobRepository jobRepository;
    private final JobExecutionRepository jobExecutionRepository;

    @Autowired
    public JobService(JobRepository jobRepository, JobExecutionRepository jobExecutionRepository) {
        this.jobRepository = jobRepository;
        this.jobExecutionRepository = jobExecutionRepository;
    }

    public Job registerJob(Job job, String submittedBy) {
        job.setCreatedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());
        job.setSubmittedBy(submittedBy);
        job.setLastModifiedBy(submittedBy);
        job.setLastAction("REGISTER");
        job.setLastActionTime(LocalDateTime.now());
        return jobRepository.save(job);
    }

    public Optional<Job> getJob(Long id) {
        return jobRepository.findById(id);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public List<JobExecution> getExecutionsForJob(Long jobId) {
        return jobExecutionRepository.findAll().stream()
                .filter(exec -> exec.getJob().getId().equals(jobId))
                .toList();
    }

    private String selectHost(List<String> hostPool) {
        // TODO: Replace with real host selection logic (e.g., check host load, availability)
        if (hostPool == null || hostPool.isEmpty()) return null;
        return hostPool.get((int) (Math.random() * hostPool.size()));
    }

    @Transactional
    public Job updateJobAction(Long jobId, JobAction action, String user) {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
        boolean dependencyCheck = (action != JobAction.FORCE_RUN);
        JobState state = job.getState();
        // Validate allowed transitions and actions
        switch (action) {
            case RUN -> {
                if (state != JobState.WAITING && state != JobState.MANUAL && state != JobState.SUCCESS && state != JobState.FAILED) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job can only be RUN from WAITING, MANUAL, SUCCESS, or FAILED state");
                }
                if (dependencyCheck && job.getDependency() != null) {
                    Job dep = job.getDependency();
                    if (dep.getState() == JobState.WAITING || dep.getState() == JobState.RUNNING) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dependency job is not complete. Cannot run this job.");
                    }
                }
                job.setState(JobState.RUNNING);
                if (job.getHostPool() == null || job.getHostPool().isEmpty()) {
                    job.setHostPool(List.of("host1", "host2", "host3"));
                }
                job.setAssignedHost(selectHost(job.getHostPool()));
            }
            case MANUAL -> {
                if (state != JobState.RUNNING) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job can only be put in MANUAL from RUNNING state");
                }
                job.setState(JobState.MANUAL);
            }
            case FORCE_RUN -> {
                if (state != JobState.WAITING) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job can only be FORCE_RUN from WAITING state");
                }
                job.setState(JobState.RUNNING);
                if (job.getHostPool() == null || job.getHostPool().isEmpty()) {
                    job.setHostPool(List.of("host1", "host2", "host3"));
                }
                job.setAssignedHost(selectHost(job.getHostPool()));
            }
            case CLEAN -> {
                if (state != JobState.RUNNING && state != JobState.CANCELLED && state != JobState.FAILED) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job can only be CLEANED (restarted) from RUNNING, CANCELLED, or FAILED state");
                }
                job.setState(JobState.WAITING);
                job.setAssignedHost(null);
                // Optionally clear executions/history here
            }
            case CANCEL -> {
                if (state != JobState.RUNNING) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job can only be CANCELLED from RUNNING state");
                }
                job.setState(JobState.CANCELLED);
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported or invalid action for current job state");
        }
        job.setAction(action);
        job.setUpdatedAt(LocalDateTime.now());
        job.setLastModifiedBy(user);
        job.setLastAction(action.name());
        job.setLastActionTime(LocalDateTime.now());
        return jobRepository.save(job);
    }
}
