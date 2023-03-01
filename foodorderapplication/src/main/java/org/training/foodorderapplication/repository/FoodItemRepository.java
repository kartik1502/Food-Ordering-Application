package org.training.foodorderapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.foodorderapplication.entity.FoodItem;
import org.training.foodorderapplication.entity.Vendor;

public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {

	List<FoodItem> findByFoodItemNameContainingIgnoreCase(String foodVendorName);

	List<FoodItem> findByVendors(Vendor vendor1);

	List<FoodItem> findByVendorsIn(List<Vendor> vendors);

}
