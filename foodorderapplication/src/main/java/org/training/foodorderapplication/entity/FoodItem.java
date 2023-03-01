package org.training.foodorderapplication.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
public class FoodItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int foodItemId;

	private String foodItemName;

	private float price;

	private int quantity;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "ItemVendors", joinColumns = @JoinColumn(name = "foodItemId", referencedColumnName = "foodItemId"), inverseJoinColumns = @JoinColumn(name = "vendorId", referencedColumnName = "vendorId"))
	private List<Vendor> vendors;
}
