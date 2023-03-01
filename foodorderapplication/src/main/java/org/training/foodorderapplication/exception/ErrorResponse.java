package org.training.foodorderapplication.exception;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

	long errorcode;
	List<String> errorMessage;

	public ErrorResponse(long errorcode, List<String> errorMessage) {
		super();
		this.errorcode = errorcode;
		this.errorMessage = errorMessage;
	}

}
