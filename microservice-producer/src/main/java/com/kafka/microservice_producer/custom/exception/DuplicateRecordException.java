package com.kafka.microservice_producer.custom.exception;

public class DuplicateRecordException extends RuntimeException {

	private static final long serialVersionUID = -747214760847127071L;

	public DuplicateRecordException(String string) {
		super(string);
	}
}
