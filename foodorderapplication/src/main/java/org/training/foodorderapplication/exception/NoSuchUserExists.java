package org.training.foodorderapplication.exception;

public class NoSuchUserExists extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;

	public NoSuchUserExists(String message) {
		super(message);
		this.message = message;
	}

	public NoSuchUserExists() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
