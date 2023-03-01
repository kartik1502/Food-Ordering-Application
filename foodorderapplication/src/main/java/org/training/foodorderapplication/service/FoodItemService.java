package org.training.foodorderapplication.service;

import java.util.List;

import org.training.foodorderapplication.dto.FoodItemDto;

public interface FoodItemService {

	List<FoodItemDto> getFoodVendorName(String foodVendorName);

}
