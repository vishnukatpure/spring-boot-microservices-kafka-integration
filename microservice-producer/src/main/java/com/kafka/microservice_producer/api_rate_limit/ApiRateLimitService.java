package com.kafka.microservice_producer.api_rate_limit;


public interface ApiRateLimitService {

	boolean isAllowed(String client, String path, String method);

}
