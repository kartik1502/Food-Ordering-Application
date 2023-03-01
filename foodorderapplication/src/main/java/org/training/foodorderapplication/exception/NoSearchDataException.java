package org.training.foodorderapplication.exception;

public class NoSearchDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;

	public NoSearchDataException(String message) {
		super(message);
	}

	public NoSearchDataException() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
