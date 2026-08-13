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
                │ Employee Consumer│
                │ employee-group   │
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
		bin\windows\kafka-consumer-groups.bat --bootstrap-server localhost:9092 --describe --group employee-group

# Future Improvements

This basic project can be extended with:

Employee JSON objects instead of String messages
Multiple Kafka partitions
Multiple consumer instances
Kafka producer acknowledgements
Retry and dead-letter topics
Error handling
Kafka message keys
Avro/JSON Schema serialization
Kafka UI
Docker-based Kafka
Spring Kafka ConcurrentKafkaListenerContainerFactory
Manual offset acknowledgement
Kafka transactions

# License

This project is intended for learning and demonstration purposes.
