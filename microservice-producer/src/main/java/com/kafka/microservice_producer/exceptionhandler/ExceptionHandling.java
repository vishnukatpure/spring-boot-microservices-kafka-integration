package com.kafka.microservice_producer.exceptionhandler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kafka.microservice_producer.custom.exception.BadRequestException;
import com.kafka.microservice_producer.custom.exception.DuplicateRecordException;
import com.kafka.microservice_producer.dto.ResponseDTO;
import com.kafka.microservice_producer.enums.StatusEnum;

@RestControllerAdvice
public class ExceptionHandling {

	@ExceptionHandler(Exception.class)
	public ResponseDTO handleException(Exception ex) {
		return new ResponseDTO().status(StatusEnum.EXCEPTION_OCCIRED).message("Something is broken !");
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseDTO badRequestException(Exception ex) {
		return new ResponseDTO().status(StatusEnum.EXCEPTION_OCCIRED).message("Something is broken !");
	}

	@ExceptionHandler(DuplicateRecordException.class)
	public ResponseDTO duplicateRecord(Exception ex) {
		return new ResponseDTO().status(StatusEnum.EXCEPTION_OCCIRED).message(ex.getMessage());
	}
}
