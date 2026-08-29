package com.kafka.microservice_producer.custom.exception;

public class TooManyRequestsException extends RuntimeException {

	private static final long serialVersionUID = -5208940924351965553L;

	public TooManyRequestsException(String reason) {
		super(reason);
	}
}
