package com.kafka.microservice_producer.rate_limit;


public interface RateLimitService {

	boolean isAllowed(String client, String path, String method);

}
