package com.balaji.test.controller;

import com.balaji.test.entity.Job;
import com.balaji.test.exception.NotFoundException;
import com.balaji.test.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobService jobService;
    JobController(JobService jobService){
        this.jobService=jobService;
    }
    @GetMapping
    public ResponseEntity<List<Job>> findAll(){
        List<Job> jobs=jobService.findAll();
        return jobs.isEmpty()
        ? new ResponseEntity<>(jobs, HttpStatus.NO_CONTENT)
                :new ResponseEntity<>(jobs,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> jobById(@PathVariable Long id) {
        Job job = jobService.jobById(id);
        if (job == null) {
            throw new NotFoundException("Job Not Found with Id :" + id);
        }
        return ResponseEntity.ok(job);
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody @Validated Job job){
        jobService.createJob(job);
        return new ResponseEntity<>("Job Successfully Created.",HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateJob(@RequestBody @Validated Job job){
        Job updatedJob=jobService.updateJob(job);
        return new ResponseEntity<>(job,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id){
        jobService.deleteJob(id);
        return new ResponseEntity<>("Job with ID "+id+" has been deleted.",HttpStatus.OK);
    }
}
