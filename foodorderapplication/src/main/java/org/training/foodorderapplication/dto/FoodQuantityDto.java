package org.training.foodorderapplication.dto;

public class FoodQuantityDto {

	public FoodQuantityDto(int foodId, VendorDto vendor, int userId) {
		super();
		this.foodId = foodId;
		this.vendor = vendor;
		this.quantity = userId;
	}

	public FoodQuantityDto() {
		// TODO Auto-generated constructor stub
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public VendorDto getVendor() {
		return vendor;
	}

	public void setVendor(VendorDto vendor) {
		this.vendor = vendor;
	}

	private int foodId;

	private VendorDto vendor;

	private int quantity;
}
