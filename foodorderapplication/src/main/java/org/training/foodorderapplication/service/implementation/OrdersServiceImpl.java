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
import org.training.foodorderapplication.dto.ResponseDto;
import org.training.foodorderapplication.entity.FoodItem;
import org.training.foodorderapplication.entity.FoodQuantity;
import org.training.foodorderapplication.entity.Orders;
import org.training.foodorderapplication.entity.Users;
import org.training.foodorderapplication.entity.Vendor;
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

	Logger logger = LoggerFactory.getLogger(OrdersServiceImpl.class);

	@Override
	public ResponseDto order(int userId, List<FoodQuantityDto> quantityDtos) {

		Optional<Users> users = userService.findById(userId);
		if (users.isEmpty()) {
			logger.error("No such user exception is thrown");
			throw new NoSuchUserExists("User with user Id:" + userId + " dose not exists");
		}

		List<String> responses = new ArrayList<>();
		List<FoodQuantity> foodQuantitys = new ArrayList<>();

		Map<Integer, Vendor> vendorMap = vendorService.findAll().stream()
				.collect(Collectors.toMap(Vendor::getVendorId, Function.identity()));

		Set<Integer> foodIds = quantityDtos.stream().map(FoodQuantityDto::getFoodId).collect(Collectors.toSet());

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

			if (e.getQuantity() > 0) {
				if (foodItem.getQuantity() >= e.getQuantity()) {
					foodItem.setQuantity(foodItem.getQuantity() - e.getQuantity());
					foodItemService.save(foodItem);
				} else {
					responses.add("Food with food Id:" + foodItem.getFoodItemId() + " has only "
							+ foodItem.getQuantity() + " quantities");
				}
			} else {
				responses.add("Quantity of required food should atleast be 1");
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
