package org.training.foodorderapplication.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.foodorderapplication.dto.FoodQuantityDto;
import org.training.foodorderapplication.dto.OrdersDto;
import org.training.foodorderapplication.dto.UsersDto;
import org.training.foodorderapplication.dto.VendorDto;
import org.training.foodorderapplication.entity.Orders;
import org.training.foodorderapplication.entity.Users;
import org.training.foodorderapplication.exception.NoSearchHistoryAvailable;
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

	@Override
	public List<OrdersDto> purchaseHistory(int userId, String filterType) {

		Optional<Users> users = userService.findById(userId);
		if (users.isEmpty()) {
			throw new NoSuchUserExists("User with user Id:" + userId + " dose not exists");
		}
		if (filterType.equalsIgnoreCase("week")) {
			List<Orders> orders = ordersRepository.findByUserWeek(userId);
			List<OrdersDto> ordersDtos = new ArrayList<>();
			orders.forEach(e -> {
				List<FoodQuantityDto> foodQuantityDtos = new ArrayList<>();
				OrdersDto ordersDto = new OrdersDto();
				e.getFoodQuantities().forEach(f -> {
					VendorDto vendorDto = new VendorDto();
					FoodQuantityDto foodQuantityDto = new FoodQuantityDto();
					BeanUtils.copyProperties(f, foodQuantityDto, "foodQuantityId");
					BeanUtils.copyProperties(f.getVendor(), vendorDto, "vendorId");
					foodQuantityDto.setVendor(vendorDto);
					foodQuantityDtos.add(foodQuantityDto);
				});
				UsersDto dto = new UsersDto();
				BeanUtils.copyProperties(e.getUser(), dto, "userId");
				ordersDto.setFoodQuantities(foodQuantityDtos);
				ordersDto.setOrderDate(e.getOrderDate());
				ordersDto.setUserDto(dto);
				ordersDtos.add(ordersDto);
			});
			if (ordersDtos.isEmpty()) {
				throw new NoSearchHistoryAvailable();
			}
			return ordersDtos;

		} else {
			List<Orders> orders = ordersRepository.findByUserMonth(userId);
			List<OrdersDto> ordersDtos = new ArrayList<>();
			orders.forEach(e -> {
				List<FoodQuantityDto> foodQuantityDtos = new ArrayList<>();
				OrdersDto ordersDto = new OrdersDto();
				e.getFoodQuantities().forEach(f -> {
					VendorDto vendorDto = new VendorDto();
					FoodQuantityDto foodQuantityDto = new FoodQuantityDto();
					BeanUtils.copyProperties(f, foodQuantityDto, "foodQuantityId");
					BeanUtils.copyProperties(f.getVendor(), vendorDto, "vendorId");
					foodQuantityDto.setVendor(vendorDto);
					foodQuantityDtos.add(foodQuantityDto);
				});
				UsersDto dto = new UsersDto();
				BeanUtils.copyProperties(e.getUser(), dto, "userId");
				ordersDto.setFoodQuantities(foodQuantityDtos);
				ordersDto.setOrderDate(e.getOrderDate());
				ordersDto.setUserDto(dto);
				ordersDtos.add(ordersDto);
			});
			if (ordersDtos.isEmpty()) {
				throw new NoSearchHistoryAvailable();
			}
			return ordersDtos;

		}
	}
}