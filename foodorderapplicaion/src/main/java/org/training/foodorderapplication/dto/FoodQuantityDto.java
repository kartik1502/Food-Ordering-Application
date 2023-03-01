package org.training.foodorderapplication.dto;

import lombok.Data;

@Data
public class FoodQuantityDto {

	private int foodId;

	private VendorDto vendorDto;

	private int quantity;

	public FoodQuantityDto(int foodId, VendorDto vendorDto	, int quantity) {
		super();
		this.foodId = foodId;
		this.vendorDto = vendorDto;
		this.quantity = quantity;
	}

	public FoodQuantityDto() {
		super();
	}

}
