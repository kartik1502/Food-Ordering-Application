package org.training.foodorderapplication.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FoodQuantity {

	public int getFoodQuantityId() {
		return foodQuantityId;
	}

	public void setFoodQuantityId(int foodQuantityId) {
		this.foodQuantityId = foodQuantityId;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
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


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int foodQuantityId;
	
	private int foodId;
	
	@ManyToOne
	private Vendor vendor;
	
	private int quantity;
}
