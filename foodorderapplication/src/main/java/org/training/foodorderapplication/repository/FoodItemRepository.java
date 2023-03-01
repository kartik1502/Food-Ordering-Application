package org.training.foodorderapplication.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.foodorderapplication.entity.FoodItem;
import org.training.foodorderapplication.entity.Vendor;

public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {

	Optional<FoodItem> findByFoodItemIdInAndVendorsIn(Set<Integer> foodIds, Collection<Vendor> vendors);

}
