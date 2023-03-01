package org.training.foodorderapplication.exception;

public class NoSearchHistoryAvailable extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String message;

	public NoSearchHistoryAvailable(String message) {
		super();
		this.message = message;
	}

	public NoSearchHistoryAvailable() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
