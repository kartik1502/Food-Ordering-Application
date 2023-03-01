package org.training.foodorderapplication.dto;

import java.util.List;

import lombok.Data;

@Data
public class ItemDto {

	
	private String itemName;

	private int price;

	private int quantity;
	
	private List<VendorDto> vendorDtos; 
}
