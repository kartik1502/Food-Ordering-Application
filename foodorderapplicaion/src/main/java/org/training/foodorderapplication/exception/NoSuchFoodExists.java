package org.training.foodorderapplication.exception;

public class NoSuchFoodExists extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;

	public NoSuchFoodExists(String message) {
		super();
		this.message = message;
	}

	public NoSuchFoodExists() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
