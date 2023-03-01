package org.training.foodorderapplication.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.foodorderapplication.entity.FoodItem;
import org.training.foodorderapplication.entity.Vendor;

public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {

	Optional<FoodItem> findByFoodItemIdAndVendors(int foodId, Vendor vendor);
  
	List<FoodItem> findByFoodItemNameContains(String foodVendorName);

	List<FoodItem> findByVendors(Vendor vendors);

}
