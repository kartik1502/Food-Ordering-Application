package org.training.foodorderapplication.service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.training.foodorderapplication.entity.FoodItem;
import org.training.foodorderapplication.entity.Vendor;

public interface FoodItemService {

	Optional<FoodItem> findById(int foodId);

	FoodItem save(FoodItem foodItem);

	Optional<FoodItem> findByFoodItemIdAndVendors(Set<Integer> foodIds, Collection<Vendor> collection);

}
