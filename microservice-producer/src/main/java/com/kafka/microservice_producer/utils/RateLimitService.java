package com.kafka.microservice_producer.utils;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.kafka.microservice_producer.config.RateLimitProperties;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

@Component
public class RateLimitService {

	private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
	private final RateLimitProperties properties;

	public RateLimitService(RateLimitProperties properties) {
		this.properties = properties;
	}

	public boolean isAllowed(String client, String path, String method) {

		RateLimitProperties.EndpointLimit config = findConfig(path, method);

		if (config == null) {
			return true;
		}

		String bucketKey = client + ":" + method + ":" + path;

		Bucket bucket = buckets.computeIfAbsent(bucketKey, key -> createBucket(config));

		return bucket.tryConsume(1);
	}

	private Bucket createBucket(RateLimitProperties.EndpointLimit config) {

		Bandwidth limit = Bandwidth.builder().capacity(config.getCapacity())
				.refillGreedy(config.getCapacity(), Duration.ofMinutes(config.getRefillMinutes())).build();

		return Bucket.builder().addLimit(limit).build();
	}

	private RateLimitProperties.EndpointLimit findConfig(String path, String method) {

		return properties.getEndpoints().stream().filter(e -> e.getMethod().equalsIgnoreCase(method))
				.filter(e -> e.getPath().equals(path)).findFirst().orElse(null);
	}
}
