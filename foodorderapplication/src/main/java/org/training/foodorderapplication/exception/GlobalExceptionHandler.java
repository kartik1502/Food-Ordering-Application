package org.training.foodorderapplication.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> errorDetails = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			errorDetails.add(error.getDefaultMessage());
		}
		ErrorResponse response = new ErrorResponse(400l, errorDetails);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoSuchUserExists.class)
	public ResponseEntity<Object> handleNoSuchUserExistsException(NoSuchUserExists ex, WebRequest req) {
		List<String> errors = new ArrayList<>();
		errors.add(ex.getLocalizedMessage());
		return new ResponseEntity<>(new ErrorResponse(404l, errors), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoSuchFoodExists.class)
	public ResponseEntity<Object> handleNoSuchFoodExistsException(NoSuchFoodExists ex, WebRequest req) {
		List<String> errors = new ArrayList<>();
		errors.add(ex.getLocalizedMessage());
		return new ResponseEntity<>(new ErrorResponse(404l, errors), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoSuchVendorExists.class)
	public ResponseEntity<Object> handleNoSuchVendorExistsException(NoSuchVendorExists ex, WebRequest req) {
		List<String> errors = new ArrayList<>();
		errors.add(ex.getLocalizedMessage());
		return new ResponseEntity<>(new ErrorResponse(404l, errors), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value =NoOrderHistoryAvailable.class)
	public ResponseEntity<Object> exception(NoOrderHistoryAvailable exception) {
		return new ResponseEntity<>("No search history available", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = NoSearchDataException.class)
	public ResponseEntity<Object> exception(NoSearchDataException exception) {
		return new ResponseEntity<>("No such Food or Vendor exists", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex, WebRequest request) {
		List<String> errors = new ArrayList<>();

		ex.getConstraintViolations().forEach(cv -> errors.add(cv.getMessage()));

		ErrorResponse response = new ErrorResponse(400l, errors);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
}
