package org.training.foodorderapplication.dto;

import java.time.LocalDate;
import java.util.List;

public class OrdersDto {
	private List<FoodQuantityDto> foodQuantities;

	private UsersDto userDto;

	private LocalDate orderDate;

	
	
	public OrdersDto(List<FoodQuantityDto> foodQuantities, UsersDto userDto, LocalDate orderDate) {
		super();
		this.foodQuantities = foodQuantities;
		this.userDto = userDto;
		this.orderDate = orderDate;
	}

	public OrdersDto() {
		
	}

	public List<FoodQuantityDto> getFoodQuantities() {
		return foodQuantities;
	}

	public void setFoodQuantities(List<FoodQuantityDto> foodQuantities) {
		this.foodQuantities = foodQuantities;
	}

	public UsersDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UsersDto userDto) {
		this.userDto = userDto;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

}
