package org.training.foodorderapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.foodorderapplication.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

}
