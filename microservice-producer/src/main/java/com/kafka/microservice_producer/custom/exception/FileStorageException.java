package com.kafka.microservice_producer.custom.exception;

public class FileStorageException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1150537880260214577L;

	public FileStorageException(String key) {
		super("File Not Found with key: " + key);
	}
}
