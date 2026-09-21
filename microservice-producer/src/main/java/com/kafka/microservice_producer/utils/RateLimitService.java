package com.kafka.microservice_producer.utils;


public interface RateLimitService {

	boolean isAllowed(String client, String path, String method);

}
