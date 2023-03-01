package org.training.foodorderapplication.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.foodorderapplication.dto.FoodQuantityDto;
import org.training.foodorderapplication.dto.FoodQuantityDtoCreate;
import org.training.foodorderapplication.dto.OrdersDto;
import org.training.foodorderapplication.dto.ResponseDto;
import org.training.foodorderapplication.dto.UsersDto;
import org.training.foodorderapplication.dto.VendorDto;
import org.training.foodorderapplication.entity.FoodItem;
import org.training.foodorderapplication.entity.FoodQuantity;
import org.training.foodorderapplication.entity.Orders;
import org.training.foodorderapplication.entity.Users;
import org.training.foodorderapplication.entity.Vendor;
import org.training.foodorderapplication.exception.NoOrderHistoryAvailable;
import org.training.foodorderapplication.exception.NoSuchFoodExists;
import org.training.foodorderapplication.exception.NoSuchUserExists;
import org.training.foodorderapplication.exception.NoSuchVendorExists;
import org.training.foodorderapplication.repository.OrdersRepository;
import org.training.foodorderapplication.service.FoodItemService;
import org.training.foodorderapplication.service.OrdersService;
import org.training.foodorderapplication.service.UserService;
import org.training.foodorderapplication.service.VendorService;


@Service
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private UserService userService;

	@Autowired
	private FoodItemService foodItemService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private OrdersRepository ordersRepository;

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
	
	@Override
	public ResponseDto order(int userId, List<FoodQuantityDtoCreate> quantityDtos) {

		Optional<Users> users = userService.findById(userId);
		if (users.isEmpty()) {
			logger.error("No such user exception is thrown");
			throw new NoSuchUserExists("User with user Id:" + userId + " dose not exists");
		}

		List<String> responses = new ArrayList<>();
		List<FoodQuantity> foodQuantitys = new ArrayList<>();

		Map<Integer, Vendor> vendorMap = vendorService.findAll().stream()
				.collect(Collectors.toMap(Vendor::getVendorId, Function.identity()));

		Set<Integer> foodIds = quantityDtos.stream().map(FoodQuantityDtoCreate::getFoodId).collect(Collectors.toSet());

		Map<Integer, FoodItem> foodItemMap = foodItemService.findByFoodItemIdAndVendors(foodIds, vendorMap.values())
				.stream().collect(Collectors.toMap(FoodItem::getFoodItemId, Function.identity()));

		quantityDtos.forEach(e -> {
			FoodQuantity foodQuantity = new FoodQuantity();

			Vendor vendor = vendorMap.get(e.getVendorId());
			if (vendor == null) {
				logger.error("No such vendor exception thrown");
				throw new NoSuchVendorExists("Vendor with vendor Id: " + e.getVendorId() + " is not present");
			}

			FoodItem foodItem = foodItemMap.get(e.getFoodId());
			if (foodItem == null) {
				logger.error("No such food exception thrown");
				throw new NoSuchFoodExists("Food with food Id: " + e.getFoodId() + " is not present for vendor Id: "
						+ vendor.getVendorId());
			}

			if (foodItem.getQuantity() >= e.getQuantity()) {
				foodItem.setQuantity(foodItem.getQuantity() - e.getQuantity());
				foodItemService.save(foodItem);
			} else {
				responses.add("Food with food Id:" + foodItem.getFoodItemId() + " has only " + foodItem.getQuantity()
						+ " quantities");
			}

			BeanUtils.copyProperties(e, foodQuantity);
			foodQuantity.setVendor(vendor);
			foodQuantitys.add(foodQuantity);
		});

		if (!responses.isEmpty()) {
			logger.info("Required amount food is not available");
			return new ResponseDto(responses, 200);
		}

		Orders orders = new Orders();
		orders.setUser(users.get());
		orders.setFoodQuantities(foodQuantitys);
		ordersRepository.save(orders);
		responses.add("Order placed Successfully");
		logger.info("Order placed Successfully");
		return new ResponseDto(responses, 200);

	}
}
