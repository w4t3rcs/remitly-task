# Remitly Task

This project is a Spring Boot application that demonstrates how to manage and retrieve SWIFT codes. It uses PostgreSQL as the main database, Redis for caching and Flyway for database migrations. The project also includes a Spring Batch job to import and process data from .csv file.

Below you will find instructions on how to build and run the project, as well as a description of its structure and exposed endpoints.

---

## Table of Contents

1. [Project Description](#project-description)
2. [Build & Run with Docker Compose](#build--run-with-docker-compose)
3. [Running Tests](#running-tests)
4. [File Tree Overview](#file-tree-overview)
5. [API Endpoints](#api-endpoints)
6. [Key Features & Technologies](#key-features--technologies)

---

## Project Description

The **Remitly Task** project allows you to:
- Store SWIFT codes in a PostgreSQL database.
- Cache frequently accessed data using Redis.
- Expose REST endpoints to retrieve SWIFT codes.
- Use Spring Batch to process CSV data and populate the database with SWIFT codes.
- Manage the schema using Flyway database migrations.

The application also comes with a set of tests leveraging Spring Boot Test, Testcontainers for integration tests with PostgreSQL and Redis, and Rest Assured for API testing.

---

## Build & Run with Docker Compose

### Prerequisites

- **Docker** installed and running
- **Docker Compose** installed

### Running the Application

1. **Clone the repository** (or download the project):
   ```bash
   git clone https://github.com/your-user/remitly-task.git
   cd remitly-task
   ```

2. **Start the containers**:
   ```bash
   docker-compose up -d
   ```
   This command will:
    - Launch a PostgreSQL container named `remitly-postgres`.
    - Launch a Redis container named `remitly-redis`.
    - Build and start the `remitly-task-app` container which runs the Spring Boot application.

3. **Verify if the application works**:
   - Check container logs if needed:
   ```bash
   docker-compose logs -f remitly-task-app
   ```

4. **Stop the containers** when done:
   ```bash
   docker-compose down
   ```

---

## Running Tests

1. **Build the project** with tests:
   ```bash
   ./mvnw clean package
   ```

2. **Run only tests**:
   ```bash
   ./mvnw test
   ```
   This will run:
    - Integration tests (backed by Testcontainers for PostgreSQL & Redis)
    - Rest Assured tests for API endpoints

> *Note:* You do **not** need to have local Postgres or Redis running. Testcontainers will automatically spin up ephemeral containers for tests.

---

## File Tree Overview

Below is a simplified overview of the main project structure:

```
remitly-task
├── src
│   ├── main
│   │   ├── java
│   │   │   └── io
│   │   │       └── w4t3rcs
│   │   │           └── task
│   │   │               ├── batch
│   │   │               │   ├── SwiftCodeRequestFieldSetMapper.java
│   │   │               │   └── SwiftCodeServiceItemWriter.java
│   │   │               ├── config
│   │   │               │   ├── AppConfig.java
│   │   │               │   └── BatchConfig.java
│   │   │               ├── controller
│   │   │               │   └── SwiftCodeController.java
│   │   │               ├── dto
│   │   │               │   ├── CountrySwiftCodeResponse.java
│   │   │               │   ├── MessageSwiftCodeResponse.java
│   │   │               │   ├── SwiftCodeRequest.java
│   │   │               │   └── SwiftCodeResponse.java
│   │   │               ├── entity
│   │   │               │   ├── Country.java
│   │   │               │   └── SwiftCode.java
│   │   │               ├── event
│   │   │               │   └── ApplicationReadyEventListener.java
│   │   │               ├── exception
│   │   │               │   └── NotFoundException.java
│   │   │               ├── service
│   │   │               │   ├── SwiftCodeService.java
│   │   │               │   └── impl
│   │   │               │       └── SwiftCodeServiceImpl.java
│   │   │               └── RemitlyTaskApplication.java
│   │   └── resources
│   │       ├── csv
│   │       │   └── data.csv
│   │       ├── db
│   │       │   └── migration
│   │       │       ├── V1__INIT_SPRING_BATCH.sql
│   │       │       └── V2__INIT_DB.sql
│   │       └── application.yaml
│   └── test
│       ├── java
│       │   └── io
|       |       └── w4t3rcs
│       │           └── task
│       │               ├── SwiftCodeCsvDataControllerTests.java
│       │               └── SwiftCodeTestDataControllerTests.java
│       └── resources
│           ├── application.yaml
│           ├── application-batch.yaml
│           └── application-no-batch.yaml
├── .env
├── docker-compose.yaml
├── Dockerfile
├── pom.xml
└── README.md                // You are here!
```

---

## API Endpoints

Below is a high-level overview of some key endpoints. All endpoints are prefixed with `/api/v1/swift` (assuming your controller uses this path):

| HTTP Method | Endpoint                                | Description                                             |
|-------------|-----------------------------------------|---------------------------------------------------------|
| **GET**     | `/v1/swift-codes/{code}`                | Retrieves details of a specific SWIFT code by its code. |
| **GET**     | `/v1/swift-codes/country/{countryIso2}` | Retrieves a list of SWIFT codes by country ISO2 code.   |
| **POST**    | `/v1/swift-codes`                       | Creates and saves to the database new SWIFT code        |
| **DELETE**  | `/v1/swift-codes/{code}`                | Removes SWIFT code from the database.                   |

> *Note:* If you need more info about the endpoints, check out the OpenAPI documentation at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

---

## Key Features & Technologies

- **Spring Boot** – Rapid application development and dependency management.
- **Spring Data JPA** – Simplified database interactions for Postgres.
- **Spring Data Redis** – Caching via Redis.
- **Spring Batch** – Batch processing for CSV input.
- **Flyway** – Database migrations.
- **Docker Compose** – Orchestrates multi-container deployments (PostgreSQL, Redis, and the app).
- **Testcontainers** – Provides ephemeral containers for integration testing.
- **Rest Assured** – Simplifies HTTP API testing.