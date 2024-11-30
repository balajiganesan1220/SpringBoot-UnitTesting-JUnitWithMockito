package com.balaji.test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @Size(min = 10, message = "Description must be at least 10 characters long")
    private String description;

    @NotNull(message = "Min Salary cannot be null")
    @Min(value = 0, message = "Min Salary must be greater than or equal to 0")
    private Double minSalary;

    @NotNull(message = "Max Salary cannot be null")
    @Min(value = 0, message = "Max Salary must be greater than or equal to 0")
    private Double maxSalary;

    @NotBlank(message = "Location cannot be blank")
    private String location;
}
