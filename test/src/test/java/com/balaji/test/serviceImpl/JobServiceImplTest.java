package com.balaji.test.serviceImpl;

import com.balaji.test.entity.Job;
import com.balaji.test.exception.NotFoundException;
import com.balaji.test.repository.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobServiceImplTest {
    @InjectMocks
    private JobServiceImpl jobService;
    @Mock
    private JobRepository jobRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void jobService_findAll_returnMoreThenOneJob() {
        Job job1 = new Job(1L, "Developer", "Coding", 50000.0, 70000.0,"Chennai");
        Job job2 = new Job(2L, "Tester", "Testing", 40000.0, 60000.0, "Bengaluru");
        when(jobRepository.findAll()).thenReturn(Arrays.asList(job1, job2));

        List<Job> jobs=jobService.findAll();

        assertEquals(2,jobs.size());
        assertEquals("Developer",jobs.get(0).getTitle());
        verify(jobRepository, times(1)).findAll();
    }

    @Test
    void jobService_jobById_returnJob() {
        Job job = new Job(1L, "Developer", "Coding", 50000.0, 70000.0,"Chennai");
        when(jobRepository.findById(job.getId())).thenReturn(Optional.of(job));

        Job result=jobService.jobById(job.getId());

        assertNotNull(result);
        assertEquals("Developer",result.getTitle());
        verify(jobRepository, times(1)).findById(job.getId());

    }

    @Test
    void jobService_findJob_returnNotFoundException(){
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException notFoundException=assertThrows(NotFoundException.class,()->jobService.jobById(1L));

        assertEquals("Job Not Found with Id :1",notFoundException.getMessage());
        verify(jobRepository, times(1)).findById(1L);
    }

    @Test
    void jobService_createJob_returnSuccessMessage() {
        Job job = new Job(1L, "Developer", "Coding", 50000.0, 70000.0,"Chennai");
        when(jobRepository.save(job)).thenReturn(job);

        jobService.createJob(job);

        verify(jobRepository, times(1)).save(job);
    }

    @Test
    void jobService_updateJob_returnUpdatedJob() {
        Job job = new Job(1L, "Developer", "Coding", 50000.0, 70000.0,"Chennai");
        Job updatedJob = new Job(1L, "Tester", "Testing", 40000.0, 60000.0, "Bengaluru");
        when(jobRepository.findById(job.getId())).thenReturn(Optional.of(job));
        when(jobRepository.save(updatedJob)).thenReturn(updatedJob);

        Job result=jobService.updateJob(updatedJob);

        assertEquals("Tester",result.getTitle());
        verify(jobRepository, times(1)).findById(job.getId());
        verify(jobRepository, times(1)).save(updatedJob);
    }

    @Test
    void jobService_updateJob_returnNotFoundException(){
        Job updatedJob = new Job(1L, "Developer", "Coding", 50000.0, 70000.0,"Chennai");
        when(jobRepository.findById(updatedJob.getId())).thenReturn(Optional.empty());

        NotFoundException notFoundException=assertThrows(NotFoundException.class,()->jobService.updateJob(updatedJob));

        assertEquals("Job Not Found with Id : 1",notFoundException.getMessage());
        verify(jobRepository, times(1)).findById(1L);
        verify(jobRepository, never()).save(updatedJob);
    }

    @Test
    void jobService_deleteJob_jobIsEmpty() {
        Job job= new Job(2L, "Tester", "Testing", 40000.0, 60000.0, "Bengaluru");
        when(jobRepository.findById(job.getId())).thenReturn(Optional.of(job));
        doNothing().when(jobRepository).delete(job);

        jobService.deleteJob(job.getId());

        verify(jobRepository, times(1)).findById(job.getId());
        verify(jobRepository, times(1)).delete(job);
    }

    @Test
    void jobService_deleteJob_jobNotFound() {
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> jobService.deleteJob(1L));

        assertEquals("Job not found wit ID : 1", notFoundException.getMessage());
        verify(jobRepository, times(1)).findById(1L);
        verify(jobRepository, never()).delete(any());
    }



}