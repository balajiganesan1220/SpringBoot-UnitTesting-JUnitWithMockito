package com.balaji.test.repository;

import com.balaji.test.entity.Job;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class JobRepositoryTest {
    @Autowired
    private JobRepository jobRepository;


    @Test
    public void jobRepository_SaveAll_ReturnSavedJob() {
        //Arrange
        Job job = new Job(null,"java developer",
                "Skills include SpringBoot",
                50000.0, 100000.0, "Chennai");

        //Act
        Job savedJob= jobRepository.save(job);

        //Assert
        assertThat(savedJob).isNotNull();
        assertThat(savedJob.getId()).isGreaterThan(0);

    }
    @Test
    public void jobRepository_findAll_returnAllJobs(){
        //Arrange
        Job job = new Job(null,"java developer",
                "Skills include SpringBoot",
                50000.0, 100000.0, "chennai");
        Job job1 = new Job(null,"SQL developer",
                "Skills include SQL",
                50000.0, 100000.0, "Bengaluru");

        jobRepository.save(job);
        jobRepository.save(job1);

        //Action
        List<Job> jobs=jobRepository.findAll();

        //Assert
        assertThat(jobs).isNotNull();
        assertThat(jobs.size()).isGreaterThan(1);

    }
    @Test
    public void jobRepository_findBYId_returnOneJob(){
        //Arrange
        Job job = new Job(null,"java developer",
                "Skills include SpringBoot",
                50000.0, 100000.0, "chennai");


        //Act
        jobRepository.save(job);
        Optional<Job> getById=jobRepository.findById(job.getId());

        //Assert
        assertThat(job.getId()).isNotNull();
        assertThat(job.getId()).isGreaterThan(0);

    }

    @Test
    public void jobRepository_findByLocation_returnJob(){
        //Arrange
        Job job = new Job(null,"java developer",
                "Skills include SpringBoot",
                50000.0, 100000.0, "Chennai");

        //Act
        jobRepository.save(job);
        Optional<Job> jobByLocation=jobRepository.findByLocation(job.getLocation());

        //Assert
        assertThat(jobByLocation).isNotNull();

    }
    @Test
    public void jobRepository_delete_returnIsEmpty(){
        //Arrange
        Job job = new Job(null,"java developer",
                "Skills include SpringBoot",
                50000.0, 100000.0, "Chennai");

        //Act
        jobRepository.save(job);
        jobRepository.deleteById(job.getId());
        Optional<Job> deletedJob=jobRepository.findById(job.getId());

        //Assert
        assertThat(deletedJob).isEmpty();

    }

}
