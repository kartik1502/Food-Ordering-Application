package org.training.foodorderapplication.dto;

import lombok.Data;

@Data
public class FoodQuantityDto {

	private int foodId;

	private int vendorId;

	private int quantity;

	public FoodQuantityDto(int foodId, int vendorId, int quantity) {
		super();
		this.foodId = foodId;
		this.vendorId = vendorId;
		this.quantity = quantity;
	}

	public FoodQuantityDto() {
		super();
	}

}
