package org.training.foodorderapplication.dto;

import lombok.Data;

@Data
public class FoodQuantityDtoCreate {

	private int foodId;

	private int vendorId;

	private int quantity;

	public FoodQuantityDtoCreate(int foodId, int vendorId, int quantity) {
		super();
		this.foodId = foodId;
		this.vendorId = vendorId;
		this.quantity = quantity;
	}

	public FoodQuantityDtoCreate() {
		super();
	}

}