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
	Java 17, 
	Spring-boot  4.1.0
	Spring Kafka 4.2.1
	Apache Kafka 4.3.1
	Swagger 		2.8.13	
	Maven 

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
	1. Manual offset acknowledgement
	2. Error handling in Single Place using @RestControllerAdvice
	3. Swagger UI (http://localhost:8081/swagger-ui/index.html)
	4. Kafka message keys
	5. Kafka JSON Schema serialization/De-serialization
	6. API access log with URL / time to process API
	7. PersonDTO passed to Broker same received in Consumer Listener
	8. Auto update createdBy, createdDate, updatedBy, UpdatedDate
	 
	

# Future Improvements

	This basic project can be extended with:

	2. Multiple Kafka partitions with Multiple consumer instances
	4. Retry and dead-letter topics. 
	5. Kafka UI 
	6. Docker-based Kafka


# License

This project is intended for learning and demonstration purposes.
