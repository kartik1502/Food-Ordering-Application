package org.training.foodorderapplication.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class FoodQuantity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int foodQuantityId;

	private String foodName;

	@ManyToOne
	private Vendor vendor;

	private int quantity;
}
