# SpringBoot-UnitTesting-JUnitWithMockito

# Job Management System

A Spring Boot application for managing job postings. It provides RESTful APIs to perform CRUD operations on job data, such as listing all jobs, retrieving a job by ID, creating a new job, updating an existing job, and deleting a job.

---

## **Features**
- Create, Read, Update, Delete (CRUD) operations for jobs.
- Exception handling for better API responses.
- Lightweight H2 database for easy testing and deployment.
- Built-in data validation for input fields.
- Comprehensive test coverage with JaCoCo.

---

## **Technologies Used**
- **Backend**: Java 17, Spring Boot
- **Database**: H2 Database (in-memory)
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito, JaCoCo

---

## **Endpoints**
| HTTP Method | Endpoint           | Description                  |
|-------------|--------------------|------------------------------|
| GET         | `/jobs`            | Retrieve all jobs            |
| GET         | `/jobs/{id}`       | Retrieve job by ID           |
| POST        | `/jobs`            | Create a new job             |
| PUT         | `/jobs/{id}`       | Update an existing job       |
| DELETE      | `/jobs/{id}`       | Delete a job by ID           |

---

## **Setup Instructions**
### Prerequisites
- Java 17
- Maven

### Steps to Run the Application
1. Clone the repository:
   ```bash
   git clone https://github.com/balajiganesan1220/SpringBoot-UnitTesting-JUnitWithMockito.git
   cd SpringBoot-UnitTesting-JUnitWithMockito
