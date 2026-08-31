# Spring Boot Microservices – Kafka Integration

A Spring Boot microservices project demonstrating **REST APIs, Apache Kafka integration, JWT authentication, caching, database encryption, auditing, monitoring, validation, optimistic locking, pagination, and API rate limiting**.

## Architecture

```text
┌──────────────────────────┐
│   Spring Boot Producer   │
│      localhost:8081      │
└────────────┬─────────────┘
             │
             │ Kafka Producer
             ▼
┌──────────────────────────┐
│      Apache Kafka        │
│      localhost:9092      │
│                          │
│      person-topic        │
└────────────┬─────────────┘
             │
             │ Kafka Consumer
             ▼
┌──────────────────────────┐
│   Person Consumer        │
│      person-group        │
│      localhost:8082      │
└────────────┬─────────────┘
             │
             ▼
      Email Notification
```

### Message Flow

```text
Client
  │
  ▼
Producer Service
  ├── JWT Authentication
  ├── Request Validation
  ├── API Rate Limiting
  ├── Cache
  └── Database
        │
        ▼
   Kafka Producer
        │
        ▼
   person-topic
        │
        ▼
  Kafka Consumer
    ├── JSON Deserialization
    ├── Manual Offset ACK
    └── Email Notification
```

## Technologies

| Technology        | Version |
| ----------------- | ------- |
| Java              | 17      |
| Spring Boot       | 4.1.0   |
| Spring Kafka      | 4.2.1   |
| Apache Kafka      | 4.3.1   |
| Swagger / OpenAPI | 2.8.13  |
| Apache Maven      | 3.2.5   |
| SLF4J             | 2.0.18  |

## Kafka Configuration

Kafka runs locally on:

```text
localhost:9092
```

The project uses **KRaft mode**, without ZooKeeper.

### Generate Storage UUID

```bat
bin\windows\kafka-storage.bat random-uuid
```

### Format Kafka Storage

```bat
bin\windows\kafka-storage.bat format --standalone -t <GENERATED-UUID> -c config\server.properties
```

### Start Kafka

```bat
bin\windows\kafka-server-start.bat config\server.properties
```

### List Topics

```bat
bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --list
```

### Describe Topic

```bat
bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --describe --topic person-topic
```

### Check Consumer Group

```bat
bin\windows\kafka-consumer-groups.bat --bootstrap-server localhost:9092 --describe --group person-group
```

## API Documentation

Swagger UI:

```text
http://localhost:8081/swagger-ui/index.html
```

## Demo Credentials

> For local learning/demo purposes only.

| Username                    | Password  |
| ----------------------------| --------- |
| testt01021990@gmail.com     | admin@123 |

## Current Implementation

### Consumer Service

| Feature                                 | Implementation                                                                                        |
| --------------------------------------- | ----------------------------------------------------------------------------------------------------- |
| **Manual Kafka Offset Acknowledgement** | Implemented manual offset acknowledgement to commit offsets only after successful message processing. |
| **Email Notification**                  | Configured email notification processing in the Kafka consumer service.                               |
| **Kafka JSON Deserialization**          | Implemented JSON deserialization of Kafka messages into `PersonDTO` objects.                          |

### Producer Service

| Feature                            | Implementation                                                                                                                                                                          |
| ---------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Centralized Exception Handling** | Implemented global exception handling using `@RestControllerAdvice` with standardized API error responses.                                                                              |
| **Swagger / OpenAPI**              | Integrated Swagger UI for interactive API documentation and API testing.                                                                                                                |
| **Kafka Message Keys**             | Implemented Kafka message keys to route messages with the same key to the same partition and consumer within a consumer group.                                                          |
| **Kafka JSON Serialization**       | Implemented JSON serialization of `PersonDTO` objects before publishing messages to Kafka.                                                                                              |
| **Custom Logging with MDC**        | Implemented API access and console logging using MDC for request/user context and request tracing.                                                                                      |
| **JPA Auditing**                   | Automatically maintains `createdBy`, `createdDate`, `updatedBy`, and `updatedDate` fields.                                                                                              |
| **In-Memory Caching**              | Enabled caching for Person data using an in-memory cache. Cache entries are cleared when the application restarts.                                                                      |
| **JWT Authentication**             | Implemented JWT-based authentication and secured REST APIs.                                                                                                                             |
| **Database Field Encryption**      | Implemented encryption of sensitive field values before storing them in the database and decryption during retrieval.                                                                   |
| **Application Monitoring**         | Integrated Spring Boot Actuator for application health, system information, JVM metrics, and HTTP metrics.                                                                              |
| **Optimistic Locking**             | Implemented optimistic locking for Person update operations to prevent concurrent update conflicts.                                                                                     |
| **Pagination & Sorting**           | Implemented pagination and sorting for the `getAllPerson` API using Spring Data `Pageable`.                                                                                             |
| **Request Validation**             | Implemented request-body validation using `@Valid` and method/path/query parameter validation using `@Validated`.                                                                       |
| **API Rate Limiting**              | Implemented Bucket4j-based API rate limiting using **Remote Address + API Endpoint + HTTP Method**, with configurable limits per API and HTTP 429 responses when the limit is exceeded. |
| **HikariCP Configuration**         | Configured HikariCP connection pooling with optimized pool size, connection timeout, idle timeout, maximum lifetime, and leak detection settings.                                       |
| **Graceful Shutdown** | Enabled Spring Boot graceful shutdown to stop accepting new requests while allowing in-flight HTTP requests and application tasks to complete before the application terminates. |
| **Database Indexing** | Added database indexes on frequently queried Person fields to improve search and query performance, particularly for filtering and pagination-related operations. |


## API Endpoints

| Method     | Endpoint           | Description                 |
| ---------  | ------------------ | --------------------------- |
| `POST`    | `/api/person`      | Create Person               |
| `GET`     | `/api/person/{id}` | Get Person                  |
| `GET`     | `/api/person/getAll`| Get Persons with pagination |
| `PUT`     | `/api/person/{id}` | Update Person               |
| `DELETE` | `/api/person/{id}` | Delete Person               |

## Future Improvements

* Multiple Kafka partitions and consumer instances
* Kafka retry mechanism
* Dead Letter Topics (DLT)
* Idempotent Kafka consumer processing
* Kafka UI for topic and consumer monitoring
* Redis-based distributed caching
* Redis-based distributed rate limiting
* Resilience4j Circuit Breaker and Retry
* Distributed tracing and correlation IDs
* Docker / Docker Compose deployment
* CI/CD pipeline
* JUnit 5 and Mockito unit tests
* Kafka integration testing with Testcontainers

## Project Objective

This project is intended for **learning, experimentation, and interview preparation**, demonstrating practical implementation of commonly used Spring Boot, Kafka, security, persistence, monitoring, and distributed-system concepts.

## License

This project is intended for learning and demonstration purposes.
