package org.training.foodorderapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.training.foodorderapplication.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

	@Query(value = "SELECT * FROM foodorderapplication.orders where WEEKOFYEAR(order_date)= WEEKOFYEAR(CURDATE())-1 and user_user_id = ?1", nativeQuery = true)
	List<Orders> findByUserWeek(int userId);

	@Query(value = "SELECT * FROM foodorderapplication.orders where `order_date` >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH) and user_user_id = ?1", nativeQuery = true)
	List<Orders> findByUserMonth(int userId);

}
