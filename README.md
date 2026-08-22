# Spring Boot Micro-services Kafka Integration 

# Architecture

	             ┌─────────────────┐
                │  Spring Boot App │
                │  localhost:8010  │
                └────────┬─────────┘
                         │
                    Producer
                         │
                         ▼
                ┌──────────────────┐
                │  Kafka Broker     │
                │  localhost:9092   │
                └────────┬─────────┘
                         │
                  employee-topic
                         │
                         ▼
                ┌──────────────────┐
                │ Person Consumer  │
                │ person-group     │
                └──────────────────┘
# Technologies
Java 17, 
Spring-boot
Spring Kafka
Apace Kafka
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
	2. Kafka producer acknowledgement
	3. Error handling in Single Place using @RestControllerAdvice
	4. Swagger UI
	5. Kafka message keys
	6. Kafka JSON Schema serialization
	 
	

# Future Improvements

	This basic project can be extended with:

	1. Employee JSON objects instead of String messages
	2. Multiple Kafka partitions
	3. Multiple consumer instances
	4. Retry and dead-letter topics. 
	5. Kafka UI (http://localhost:8081/swagger-ui/index.html)
	6. Docker-based Kafka


# License

This project is intended for learning and demonstration purposes.
