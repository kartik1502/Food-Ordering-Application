package org.training.foodorderapplication.service;

import java.util.Optional;
import java.util.List;

import org.training.foodorderapplication.dto.FoodItemDto;
import org.training.foodorderapplication.entity.FoodItem;
import org.training.foodorderapplication.entity.Vendor;

public interface FoodItemService {

	Optional<FoodItem> findById(int foodId);

	FoodItem save(FoodItem foodItem);

	Optional<FoodItem> findByFoodItemIdAndVendors(int foodId, Vendor vendor);

	List<FoodItemDto> getFoodVendorName(String foodVendorName);

}
