package com.vamshi.jobOrchestrator.api;

import com.vamshi.jobOrchestrator.config.LoggerFactory;
import com.vamshi.jobOrchestrator.engine.JobService;
import com.vamshi.jobOrchestrator.model.Job;
import com.vamshi.jobOrchestrator.model.JobAction;
import com.vamshi.jobOrchestrator.model.JobExecution;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<Job> registerJob(@RequestBody Job job, @RequestParam String user) {
        Job created = jobService.registerJob(job, user);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/{id}/run")
    public ResponseEntity<Job> runJob(@PathVariable Long id, @RequestParam String user) {
        Job updated = jobService.updateJobAction(id, JobAction.RUN, user);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJob(@PathVariable Long id) {
        return jobService.getJob(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/{id}/executions")
    public List<JobExecution> getExecutionsForJob(@PathVariable Long id) {
        return jobService.getExecutionsForJob(id);
    }

    @PostMapping("/{id}/clean")
    public ResponseEntity<Job> cleanJob(@PathVariable Long id, @RequestParam String user) {
        logger.info("User '{}' requested CLEAN for job {}", user, id);
        Job updated = jobService.updateJobAction(id, JobAction.CLEAN, user);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/kill")
    public ResponseEntity<Job> killJob(@PathVariable Long id, @RequestParam String user) {
        logger.info("User '{}' requested KILL for job {}", user, id);
        Job updated = jobService.updateJobAction(id, JobAction.CANCEL, user);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/manual")
    public ResponseEntity<Job> manualJob(@PathVariable Long id, @RequestParam String user) {
        logger.info("User '{}' requested MANUAL for job {}", user, id);
        Job updated = jobService.updateJobAction(id, JobAction.MANUAL, user);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/force-run")
    public ResponseEntity<Job> forceRunJob(@PathVariable Long id, @RequestParam String user) {
        logger.info("User '{}' requested FORCE_RUN for job {}", user, id);
        Job updated = jobService.updateJobAction(id, JobAction.FORCE_RUN, user);
        return ResponseEntity.ok(updated);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleBadRequest(ResponseStatusException ex) {
        logger.warn("User error: {} - {}", ex.getStatusCode(), ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body("Error: " + ex.getReason());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherExceptions(Exception ex) {
        logger.error("Unexpected error: ", ex);
        return ResponseEntity.status(500).body("Internal server error. Please contact support.");
    }
}
