package org.training.foodorderapplication.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResponseDto {

	private List<String> responseMessage;

	private int responseCode;

	public ResponseDto(List<String> responseMessage, int responseCode) {
		super();
		this.responseMessage = responseMessage;
		this.responseCode = responseCode;
	}

	public ResponseDto() {
		super();
	}

}
