package com.balaji.test.controller;

import com.balaji.test.entity.Job;
import com.balaji.test.exception.GlobalExceptionHandler;
import com.balaji.test.exception.NotFoundException;
import com.balaji.test.service.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;


import java.util.Collections;
import java.util.List;


import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



class JobControllerTest {
    private MockMvc mockMvc;
    @Mock
    private JobService jobService;
    @InjectMocks
    private JobController jobController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(jobController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }
    @Test
    void jobController_findAll_returnJobList() throws Exception {
        Job job1 = new Job(1L, "Developer", "Coding", 50000.0, 100000.0, "Chennai");
        List<Job> jobs = Collections.singletonList(job1);
        when(jobService.findAll()).thenReturn(jobs);

        mockMvc.perform(get("/jobs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Developer"))
                .andExpect(jsonPath("$[0].location").value("Chennai"));

        verify(jobService, times(1)).findAll();
    }

    @Test
    void jobController_findAll_returnEmptyList() throws Exception {
        when(jobService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/jobs"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").isEmpty());

        verify(jobService, times(1)).findAll();
    }


    @Test
    void jobController_jobById_returnJob() throws Exception {
        Job job1 = new Job(1L, "Developer", "Coding", 50000.0, 100000.0, "Chennai");
        when(jobService.jobById(1L)).thenReturn(job1);

        mockMvc.perform(get("/jobs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Developer"));

        verify(jobService, times(1)).jobById(1L);

    }

    @Test
    void jobController_jobById_throwNotFoundException() throws Exception {
        when(jobService.jobById(1L)).thenThrow(new NotFoundException("Job Not Found with Id :1"));

        mockMvc.perform(get("/jobs/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.message").value("Job Not Found with Id :1"))
                .andExpect(jsonPath("$.details").value("Job Not Found"));
        verify(jobService, times(1)).jobById(1L);
    }

    @Test
    void jobController_jobById_invalidId_returnsNotFound() throws Exception {
        mockMvc.perform(get("/jobs/{id}", -1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Job Not Found with Id :-1"));
    }


    @Test
    void jobController_createJob_returnSuccessMessage() throws Exception {
        Job job1 = new Job(1L, "Developer", "Coding", 50000.0, 100000.0, "Chennai");
        doNothing().when(jobService).createJob(any(Job.class));

        mockMvc.perform(post("/jobs")
                        .contentType("application/json")
                        .content("{\"id\":1,\"title\":\"Developer\",\"description\":\"Master in Java Coding\",\"minSalary\":\"50000.0\",\"maxSalary\":\"100000.0\",\"location\":\"Chennai\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Job Successfully Created."));

        verify(jobService, times(1)).createJob(any(Job.class));

    }

    @Test
    void jobController_createJob_invalidInput_returnsBadRequest() throws Exception {
        String invalidJobJson = """
        {
            "title": "",
            "description": "Short"
        }
    """;

        mockMvc.perform(post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJobJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title cannot be blank"))
                .andExpect(jsonPath("$.description").value("Description must be at least 10 characters long"));
    }


    @Test
    void jobController_updateJob_returnUpdatedJob () throws Exception {
        Job job = new Job(1L, "Developer", "Coding", 50000.0, 100000.0, "Chennai");
        when(jobService.updateJob(any(Job.class))).thenReturn(job);

        mockMvc.perform(put("/jobs")
                .contentType("application/json")
                .content("{\"id\":1,\"title\":\"Developer\",\"description\":\"Master in Java Coding\",\"minSalary\":\"50000.0\",\"maxSalary\":\"100000.0\",\"location\":\"Chennai\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Developer"));

        verify(jobService, times(1)).updateJob(any(Job.class));

    }

    @Test
    void jobController_updateJob_invalidInput_returnsBadRequest() throws Exception {
        String invalidJobJson = """
    {
        "id": 1,
        "title": "",
        "description": "",
        "minSalary": 50000.0,
        "maxSalary": 100000.0,
        "location": "Chennai"
    }
    """;

        mockMvc.perform(put("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJobJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title cannot be blank"))
                .andExpect(jsonPath("$.description").value("Description must be at least 10 characters long"));
    }


    @Test
    void jobController_deleteJobById_returnJobDeleted() throws Exception {
        Job job = new Job(1L, "Developer", "Coding", 50000.0, 100000.0, "Chennai");
        doNothing().when(jobService).deleteJob(job.getId());

        mockMvc.perform(delete("/jobs/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Job with ID 1 has been deleted."));

        verify(jobService,times(1)).deleteJob(job.getId());
    }

    @Test
    void jobController_deleteJobById_jobNotFound_returnsNotFound() throws Exception {
        doThrow(new NotFoundException("Job Not Found with Id :1")).when(jobService).deleteJob(1L);

        mockMvc.perform(delete("/jobs/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Job Not Found with Id :1"));
    }

}