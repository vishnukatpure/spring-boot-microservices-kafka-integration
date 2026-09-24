package com.kafka.microservice_producer.exceptionhandler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kafka.microservice_producer.custom.exception.BadRequestException;
import com.kafka.microservice_producer.custom.exception.DuplicateRecordException;
import com.kafka.microservice_producer.custom.exception.TooManyRequestsException;
import com.kafka.microservice_producer.dto.ResponseDTO;
import com.kafka.microservice_producer.enums.StatusEnum;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionHandling {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDTO> handleException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ResponseDTO().status(StatusEnum.EXCEPTION_OCCIRED).message("Something is broken !"));
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ResponseDTO> badRequestException(BadRequestException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ResponseDTO().status(StatusEnum.EXCEPTION_OCCIRED).message(ex.getMessage()));
	}

	@ExceptionHandler(DuplicateRecordException.class)
	public ResponseEntity<ResponseDTO> duplicateRecord(DuplicateRecordException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ResponseDTO().status(StatusEnum.DUPLICATE_RECORD).message(ex.getMessage()));
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ResponseDTO> noRecordFound(NoSuchElementException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ResponseDTO().status(StatusEnum.NOT_FOUND).message("Record Not Found.!"));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ResponseDTO().status(StatusEnum.VALIDATION_FAILURE).object(errors));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ResponseDTO> handleConstraintViolation(ConstraintViolationException ex) {

		Map<String, String> errors = new HashMap<>();

		ex.getConstraintViolations()
				.forEach(error -> errors.put(error.getPropertyPath().toString(), error.getMessage()));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ResponseDTO().status(StatusEnum.VALIDATION_FAILURE).object(errors));
	}

	@ExceptionHandler(TooManyRequestsException.class)
	public ResponseEntity<ResponseDTO> handleRateLimit(TooManyRequestsException ex) {
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
				.body(new ResponseDTO().status(StatusEnum.TOO_MANY_REQUESTS).message("API Rate limit exceeded"));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ResponseDTO> handleDuplicateRecord(DataIntegrityViolationException ex) {
		String message = ex.getMessage();
		String value = "unknown";
		if (message != null) {
			int first = message.indexOf("'");
			int second = first >= 0 ? message.indexOf("'", first + 1) : -1;
			if (first >= 0 && second > first) {
				value = message.substring(first + 1, second);
			}
		}
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ResponseDTO().status(StatusEnum.DUPLICATE_RECORD).message("Duplicate entry [" + value + "]"));
	}

	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<ResponseDTO> handleAccessDenied(AuthorizationDeniedException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(new ResponseDTO().status(StatusEnum.PERMISSION_DENIED).message("You Dont have permission !"));
	}

}
