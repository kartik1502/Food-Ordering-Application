package org.training.foodorderapplication.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.foodorderapplication.entity.FoodItem;
import org.training.foodorderapplication.entity.Vendor;
import org.training.foodorderapplication.repository.FoodItemRepository;
import org.training.foodorderapplication.service.FoodItemService;

@Service
public class FoodItemServiceImpl implements FoodItemService {
	
	@Autowired
	private FoodItemRepository repository;

	@Override
	public Optional<FoodItem> findById(int foodId) {
		return repository.findById(foodId);
	}

	@Override
	public FoodItem save(FoodItem foodItem) {
		return repository.save(foodItem);
	}

	@Override
	public Optional<FoodItem> findByFoodItemIdAndVendors(int foodId, Vendor vendor) {
		return repository.findByFoodItemIdAndVendors(foodId, vendor);
	}

}
