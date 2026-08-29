package com.kafka.microservice_producer.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rate-limit")
public class RateLimitProperties {
	private List<EndpointLimit> endpoints;

	public List<EndpointLimit> getEndpoints() {
		return endpoints;
	}

	public void setEndpoints(List<EndpointLimit> endpoints) {
		this.endpoints = endpoints;
	}

	public static class EndpointLimit {

		private String path;
		private String method;
		private long capacity;
		private long refillMinutes;

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public long getCapacity() {
			return capacity;
		}

		public void setCapacity(long capacity) {
			this.capacity = capacity;
		}

		public long getRefillMinutes() {
			return refillMinutes;
		}

		public void setRefillMinutes(long refillMinutes) {
			this.refillMinutes = refillMinutes;
		}

	}
}
