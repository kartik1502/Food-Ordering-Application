package org.training.foodorderapplication.dto;

import java.util.List;

import lombok.Data;

@Data
public class FoodItemDto {

	private String foodItemName;

	private float price;

	private int quantity;

	private List<VendorDto> vendorDtos;
}
