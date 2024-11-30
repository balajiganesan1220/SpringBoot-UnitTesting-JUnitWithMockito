package com.balaji.test.serviceImpl;

import com.balaji.test.entity.Job;
import com.balaji.test.exception.NotFoundException;
import com.balaji.test.repository.JobRepository;
import com.balaji.test.service.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    JobServiceImpl(JobRepository jobRepository){
        this.jobRepository=jobRepository;
    }
    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public Job jobById(Long id) {
        return jobRepository.findById(id).orElseThrow(() -> new NotFoundException("Job Not Found with Id :" + id));
    }

    @Override
    public void createJob(Job job) {
      jobRepository.save(job);
    }

    @Override
    public Job updateJob(Job job) {
        Job updatedJob=jobRepository.findById(job.getId())
                .orElseThrow(()->new NotFoundException("Job Not Found with Id : "+job.getId()));
        updatedJob.setDescription(job.getDescription());
        updatedJob.setLocation(job.getLocation());
        updatedJob.setTitle(job.getTitle());
        updatedJob.setMinSalary(job.getMinSalary());
        updatedJob.setMaxSalary(job.getMaxSalary());
        return jobRepository.save(updatedJob);
    }

    @Override
    public void deleteJob(Long id) {
       Job job=jobRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Job not found wit ID : " + id));
        jobRepository.delete(job);
    }
}
