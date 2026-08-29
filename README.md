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

1. **Manual Kafka Offset Acknowledgement** – Controls offset commits and acknowledges messages only after successful processing.

2. **Email Notification** – Sends email notifications from the Kafka consumer service.

3. **Kafka JSON Deserialization** – Deserializes Kafka JSON messages into `PersonDTO` objects.

### Producer Service

1. **Centralized Exception Handling** – Global exception handling using `@RestControllerAdvice` with standardized API responses.

2. **Swagger / OpenAPI** – Interactive API documentation and testing using Swagger UI.

3. **Kafka Message Keys** – Uses Kafka message keys to route messages with the same key to the same partition and, within a consumer group, to the consumer assigned to that partition.

4. **Kafka JSON Serialization** – Serializes `PersonDTO` objects into JSON before publishing messages to Kafka.

5. **Custom Logging with MDC** – API access and console logging with MDC-based request/user context for request tracing.

6. **JPA Auditing** – Automatically maintains:
   	* `createdBy`
   	* `createdDate`
   	* `updatedBy`
   	* `updatedDate`

7. **In-Memory Caching** – Caches Person data in memory. Cache entries are cleared when the application restarts.

8. **JWT Authentication** – Secures REST APIs using JWT-based authentication.

9. **Database Field Encryption** – Encrypts sensitive values before storing them in the database and decrypts them during retrieval.

10. **Application Monitoring** – Spring Boot Actuator for application health, system information, JVM metrics, and HTTP metrics.

    ```text
    GET /actuator/health
    GET /actuator/info
    GET /actuator/metrics
    GET /actuator/metrics/jvm.memory.used
    GET /actuator/metrics/system.cpu.usage
    GET /actuator/metrics/http.server.requests
    ```

11. **Optimistic Locking** – Prevents concurrent update conflicts during Person update operations.

12. **Pagination & Sorting** – Implemented pagination and sorting for the `getAllPerson` API using Spring Data `Pageable`.

13. **Request Validation** – Request-body validation using `@Valid` and method/path/query parameter validation using `@Validated`.

14. **API Rate Limiting** – Implemented Bucket4j-based rate limiting using **Remote Address + API Endpoint + HTTP Method**, with configurable limits per API and HTTP 429 responses when the limit is exceeded.

## API Endpoints

| Method   | Endpoint           | Description                 |
| -------- | ------------------ | --------------------------- |
| `POST`   | `/api/person`      | Create Person               |
| `GET`    | `/api/person/{id}` | Get Person                  |
| `GET`    | `/api/person`      | Get Persons with pagination |
| `PUT`    | `/api/person/{id}` | Update Person               |
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
