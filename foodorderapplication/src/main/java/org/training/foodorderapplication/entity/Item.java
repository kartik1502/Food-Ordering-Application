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

@Entity
public class Item {

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

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

	public List<Vendor> getVendors() {
		return vendors;
	}

	public void setVendors(List<Vendor> vendors) {
		this.vendors = vendors;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int itemId;
	
	private String itemName;
	
	private int price;
	
	private int quantity;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "ItemVendors",
			joinColumns = @JoinColumn(name = "itemId", referencedColumnName = "itemId"),
			inverseJoinColumns = @JoinColumn(name = "vendorId", referencedColumnName = "vendorId"))
	private List<Vendor> vendors;
}
