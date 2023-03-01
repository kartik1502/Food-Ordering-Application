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
import org.training.foodorderapplication.exception.NoSearchHistoryAvailable;
import org.training.foodorderapplication.repository.OrdersRepository;
import org.training.foodorderapplication.service.OrdersService;
import org.training.foodorderapplication.service.UserService;

import org.training.foodorderapplication.dto.ResponseDto;
import org.training.foodorderapplication.entity.FoodItem;
import org.training.foodorderapplication.entity.FoodQuantity;
import org.training.foodorderapplication.entity.Orders;
import org.training.foodorderapplication.entity.Users;
import org.training.foodorderapplication.entity.Vendor;
import org.training.foodorderapplication.exception.NoSuchFoodExists;
import org.training.foodorderapplication.exception.NoSuchUserExists;
import org.training.foodorderapplication.exception.NoSuchVendorExists;
import org.training.foodorderapplication.service.FoodItemService;
import org.training.foodorderapplication.service.VendorService;

@Service
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	OrdersRepository ordersRepository;

	@Autowired
	private UserService userService;
  
	@Autowired
	private FoodItemService foodItemService;

	@Autowired
	private VendorService vendorService;
	
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
	
	@Override
	public ResponseDto order(int userId, List<FoodQuantityDto> quantityDtos) {
  
		List<String> responses = new ArrayList<>();
		List<FoodQuantity> foodQuantitys = new ArrayList<>();
		quantityDtos.forEach(e -> {
			
			FoodQuantity foodQuantity = new FoodQuantity();
			
			Optional<Vendor> vendors = vendorService.findById(e.getVendorId());
			if (vendors.isEmpty()) {
				throw new NoSuchVendorExists("Vendor with vendor Id: " + e.getVendorId() + " is not present");
			}
			
			Optional<FoodItem> foodItem = foodItemService.findByFoodItemIdAndVendors(e.getFoodId(), vendors.get());
			if (foodItem.isEmpty()) {
				throw new NoSuchFoodExists("Food with food Id: " + e.getFoodId() + " is not present for vendor Id: "+vendors.get().getVendorId());
			}
			
			if(foodItem.get().getQuantity() >= e.getQuantity()) {
				foodItem.get().setQuantity(foodItem.get().getQuantity() - e.getQuantity());
				foodItemService.save(foodItem.get());
			}
			
			else {
				responses.add("Food with food Id:"+foodItem.get().getFoodItemId()+" has only "+foodItem.get().getQuantity()+" quantities");
			}
			BeanUtils.copyProperties(e, foodQuantity);
			foodQuantity.setVendor(vendors.get());
			foodQuantitys.add(foodQuantity);
		});

		if(!responses.isEmpty()) {
			return new ResponseDto(responses, 200); 
		}
		Orders orders = new Orders();
		orders.setUser(users.get());
		orders.setFoodQuantities(foodQuantitys);
		ordersRepository.save(orders);
		responses.add("Order placed Successfully");
		return new ResponseDto(responses, 200);
	}

}
