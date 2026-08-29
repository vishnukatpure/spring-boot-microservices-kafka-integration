package com.kafka.microservice_producer.exceptionhandler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kafka.microservice_producer.custom.exception.BadRequestException;
import com.kafka.microservice_producer.custom.exception.DuplicateRecordException;
import com.kafka.microservice_producer.dto.ResponseDTO;
import com.kafka.microservice_producer.enums.StatusEnum;

import jakarta.validation.ConstraintViolationException;

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

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseDTO noRecordFound(Exception ex) {
		return new ResponseDTO().status(StatusEnum.EXCEPTION_OCCIRED).message("Record Not Found.!");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseDTO handleValidationException(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		return new ResponseDTO().status(StatusEnum.VALIDATION_FAILURE).object(errors);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseDTO handleConstraintViolation(ConstraintViolationException ex) {

		Map<String, String> errors = new HashMap<>();

		ex.getConstraintViolations()
				.forEach(error -> errors.put(error.getPropertyPath().toString(), error.getMessage()));

		return new ResponseDTO().status(StatusEnum.VALIDATION_FAILURE).object(errors);
	}
}
