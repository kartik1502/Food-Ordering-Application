package org.training.foodorderapplication.exception;

import java.util.List;

public class ErrorResponse {

	private long errorCode;

	private List<String> errorDetails;

	public ErrorResponse(long errorCode, List<String> errorDetails) {
		super();
		this.errorCode = errorCode;
		this.errorDetails = errorDetails;
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

	public List<String> getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(List<String> errorDetails) {
		this.errorDetails = errorDetails;
	}
}
