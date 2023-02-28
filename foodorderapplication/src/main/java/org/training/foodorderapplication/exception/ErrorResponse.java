package org.training.foodorderapplication.exception;

import java.util.List;

public class ErrorResponse {

	private long errorCode;

	private List<String> errors;

	public ErrorResponse(long errorCode, List<String> errors) {
		super();
		this.errorCode = errorCode;
		this.errors = errors;
	}

	public ErrorResponse() {
		super();
	}

	public long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
