package com.balaji.test.service;

import com.balaji.test.entity.Job;
import com.balaji.test.serviceImpl.JobServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface JobService{
    List<Job> findAll();

    Job jobById(Long id);

    void createJob(Job job);

    Job updateJob(Job job);

    void deleteJob(Long id);
}
