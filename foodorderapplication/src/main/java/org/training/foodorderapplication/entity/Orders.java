package org.training.foodorderapplication.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ordersId;
	
	@OneToMany
	private List<FoodQuantity> foodQuantities;
	
	@ManyToOne
	private Users user;
	
	private LocalDate orderDate;

	public int getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(int ordersId) {
		this.ordersId = ordersId;
	}

	public List<FoodQuantity> getFoodQuantities() {
		return foodQuantities;
	}

	public void setFoodQuantities(List<FoodQuantity> foodQuantities) {
		this.foodQuantities = foodQuantities;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}


	
}
