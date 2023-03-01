package org.training.foodorderapplication.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.foodorderapplication.dto.FoodQuantityDto;
import org.training.foodorderapplication.dto.OrdersDto;
import org.training.foodorderapplication.dto.UsersDto;
import org.training.foodorderapplication.dto.VendorDto;
import org.training.foodorderapplication.entity.Orders;
import org.training.foodorderapplication.entity.Users;
import org.training.foodorderapplication.exception.NoOrderHistoryAvailable;
import org.training.foodorderapplication.exception.NoSuchUserExists;
import org.training.foodorderapplication.repository.OrdersRepository;
import org.training.foodorderapplication.service.OrdersService;
import org.training.foodorderapplication.service.UserService;

@Service
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	OrdersRepository ordersRepository;

	@Autowired
	UserService userService;

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public List<OrdersDto> purchaseHistory(int userId, String filterType) {

		Optional<Users> users = userService.findById(userId);
		  if (users.isEmpty()) {
			  logger.error("NoSuchUserFound Exception is thrown");
		   throw new NoSuchUserExists("User with user Id:" + userId + " dose not exists");
		  }
		  List<Orders> orders;
		  if (filterType.equalsIgnoreCase("week")) {
			 
		   orders = ordersRepository.findByUserWeek(userId);
		  } else {
			 
		   orders = ordersRepository.findByUserMonth(userId);
		  }

		  List<OrdersDto> ordersDtos = orders.stream()
		    .map(order -> new OrdersDto(order.getFoodQuantities().stream()
		      .map(foodQuantity -> new FoodQuantityDto(foodQuantity.getQuantity(),
		        new VendorDto(foodQuantity.getVendor().getVendorName(),
		          foodQuantity.getVendor().getCompanyName()),
		        userId))
		      .collect(Collectors.toList()),
		      new UsersDto(order.getUser().getUserName(), order.getUser().getUserEmail()),
		      order.getOrderDate()))
		    .collect(Collectors.toList());

		  if (ordersDtos.isEmpty()) {
			  logger.error("NoOrderHistoryAvailable Exception is thrown");
		   throw new NoOrderHistoryAvailable();
		  }
		  logger.info("Order history is displayed");
		  return ordersDtos;
	}
}
