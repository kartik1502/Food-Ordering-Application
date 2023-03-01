package org.training.foodorderapplication.exception;

public class NoSuchVendorExists extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;

	public NoSuchVendorExists(String message) {
		super();
		this.message = message;
	}

	public NoSuchVendorExists() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
