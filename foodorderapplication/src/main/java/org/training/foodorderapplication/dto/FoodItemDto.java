package org.training.foodorderapplication.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class FoodItemDto {

	@NotNull
	@Pattern(regexp = "[a-zA-Z ]+", message = "Enter a valid Item name")
	private String itemName;

	private float price;

	private int quantity;

	private List<VendorDto> vendorDtos;
}
