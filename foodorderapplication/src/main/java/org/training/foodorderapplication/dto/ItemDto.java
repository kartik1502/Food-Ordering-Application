package org.training.foodorderapplication.dto;

import java.util.List;

public class ItemDto {

	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public List<VendorDto> getVendorDtos() {
		return vendorDtos;
	}

	public void setVendorDtos(List<VendorDto> vendorDtos) {
		this.vendorDtos = vendorDtos;
	}

	private String itemName;

	private int price;

	private int quantity;
	
	private List<VendorDto> vendorDtos; 
}
