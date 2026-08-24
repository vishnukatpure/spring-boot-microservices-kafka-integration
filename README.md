# Spring Boot Micro-services Kafka Integration 

# Architecture

	             ┌──────────────────┐
                │  Spring Boot App │
                │  Message Producer│                
                │  localhost:8081  │
                └────────┬─────────┘
                         │
                    Producer
                         │
                         ▼
                ┌──────────────────┐
                │  Kafka Broker    │
                │  localhost:9092  │
                └────────┬─────────┘
                         │
                  Consumer(person-topic)
                         │
                         ▼
                ┌──────────────────┐
                │ Person Consumer  │
                │ person-group     │
                │ Localhost:8082   │              
                └──────────────────┘
# Technologies
	Java         17 
	Spring-boot  4.1.0
	Spring Kafka 4.2.1
	Apache Kafka 4.3.1
	Swagger UI   2.8.13	
	Apache Maven 3.2.5
	Slf4j        2.0.18
	 

# Kafka Configuration
Kafka is running locally on: 9092(Default)
Few Commands to remember

	Generfate Random UUID for storage
		bin\windows\kafka-storage.bat random-uuid

	Format created storate
		bin\windows\kafka-storage.bat format --standalone -t zMZJNlcGTb62vJDRC71YTw -c config\server.properties

	Start Kafka Server
		bin\windows\kafka-server-start.bat config\server.properties

	Check Kafka Topic list
		bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --list

	Describe Topic
		bin\windows\kafka-topics.bat -bootstrap-server localhost:9092 -describe -topic employee-topic

	Count Topic messages
		bin\windows\kafka-consumer-groups.bat --bootstrap-server localhost:9092 --describe --group person-group

# Current Implementation
	
	Consumer Service, 
	1. Manual offset acknowledgement
	2. Email Configuration
	3. Kafka JSON Schema De-serialization(PersonDTO)
	
	Producer Service
	
	1. Error handling in Single Place using @RestControllerAdvice
	2. Swagger UI (http://localhost:8081/swagger-ui/index.html)
	3. Kafka message keys to maintain same Consumer
	4. Kafka JSON Schema serialization (PersonDTO)
	5. API access log with URL / time to process API
	6. Auto update createdBy, createdDate, updatedBy, UpdatedDate
	7. Enabled Cache for Person Table (in-memory cache so it will empty after restart server)
	8. JWT Token enabled
	 
	

# Future Improvements

	This basic project can be extended with:

	Multiple Kafka partitions with Multiple consumer instances
	Retry and dead-letter topics. 
	Kafka UI 
	Docker-based Kafka


# License

This project is intended for learning and demonstration purposes.
