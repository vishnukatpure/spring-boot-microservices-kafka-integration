package com.kafka.microservice_producer.api_rate_limit;

import java.time.Duration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;

@Service
@ConditionalOnProperty(name = "api-rate-limit.type", havingValue = "redis")
public class ApiRateLimitRedisService implements ApiRateLimitService {

	private final ApiRateLimitProperties properties;
	private final ProxyManager<String> proxyManager;

	public ApiRateLimitRedisService(ApiRateLimitProperties properties, ProxyManager<String> proxyManager) {
		this.properties = properties;
		this.proxyManager = proxyManager;
	}

	@Override
	public boolean isAllowed(String client, String path, String method) {

		ApiRateLimitProperties.EndpointLimit config = findConfig(path, method);

		if (config == null) {
			return true;
		}

		String bucketKey = client + ":" + method + ":" + path;

		Bucket bucket = proxyManager.builder().build(bucketKey, () -> createConfiguration(config));

		return bucket.tryConsume(1);
	}

	private BucketConfiguration createConfiguration(ApiRateLimitProperties.EndpointLimit config) {

		return BucketConfiguration.builder().addLimit(limit -> limit.capacity(config.getCapacity())
				.refillGreedy(config.getCapacity(), Duration.ofMinutes(config.getRefillMinutes()))).build();
	}

	private ApiRateLimitProperties.EndpointLimit findConfig(String path, String method) {

		return properties.getEndpoints().stream().filter(e -> e.getMethod().equalsIgnoreCase(method))
				.filter(e -> e.getPath().equals(path)).findFirst().orElse(null);
	}
}
