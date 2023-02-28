package org.training.foodorderapplication.service;

import java.util.Optional;

import org.training.foodorderapplication.entity.FoodItem;
import org.training.foodorderapplication.entity.Vendor;

public interface FoodItemService {

	Optional<FoodItem> findById(int foodId);

	FoodItem save(FoodItem foodItem);

	Optional<FoodItem> findByFoodItemIdAndVendors(int foodId, Vendor vendor);

}
