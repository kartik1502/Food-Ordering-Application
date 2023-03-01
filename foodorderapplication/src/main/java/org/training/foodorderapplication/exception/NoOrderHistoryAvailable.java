package org.training.foodorderapplication.exception;

public class NoOrderHistoryAvailable extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String message;

	public NoOrderHistoryAvailable(String message) {
		super();
		this.message = message;
	}

	public NoOrderHistoryAvailable() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
