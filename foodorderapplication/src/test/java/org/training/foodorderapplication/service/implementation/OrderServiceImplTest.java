package org.training.foodorderapplication.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.training.foodorderapplication.dto.FoodQuantityDto;
import org.training.foodorderapplication.dto.OrdersDto;
import org.training.foodorderapplication.dto.UsersDto;
import org.training.foodorderapplication.dto.VendorDto;
import org.training.foodorderapplication.entity.FoodQuantity;
import org.training.foodorderapplication.entity.Orders;
import org.training.foodorderapplication.entity.Users;
import org.training.foodorderapplication.entity.Vendor;
import org.training.foodorderapplication.exception.NoSuchUserExists;
import org.training.foodorderapplication.repository.OrdersRepository;
import org.training.foodorderapplication.service.UserService;

@ExtendWith(SpringExtension.class)
public class OrderServiceImplTest {

	@Mock
	private OrdersRepository ordersRepository;

	@InjectMocks
	private OrdersServiceImpl ordersService;
	
	@Mock
	private UserService userService;

	@Test
	public void testPurchaseHistoryWeek() {
		int userId = 1;
		Users user = new Users();
		user.setUserId(1);
		Optional<Users> userOptional = Optional.of(user);
		Mockito.when(userService.findById(1)).thenReturn(userOptional);
		String filterType = "week";
		List<Orders> orders = new ArrayList<>();
		Orders order = new Orders();
		order.setOrderDate(LocalDate.now());
		FoodQuantity foodQuantity = new FoodQuantity();
		foodQuantity.setFoodQuantityId(1);
		Vendor vendor = new Vendor();
		vendor.setVendorId(1);
		foodQuantity.setVendor(vendor);
		order.setFoodQuantities(Arrays.asList(foodQuantity));
		order.setUser(user);
		orders.add(order);
		Mockito.when(ordersRepository.findByUserWeek(userId)).thenReturn(orders);

		List<OrdersDto> expectedOrders = new ArrayList<>();
		OrdersDto expectedOrder = new OrdersDto();
		expectedOrder.setOrderDate(LocalDate.now());
		FoodQuantityDto foodQuantityDto = new FoodQuantityDto();
		foodQuantityDto.setFoodId(1);
		VendorDto vendorDto = new VendorDto();
		vendorDto.setVendorName("nadiya");
		foodQuantityDto.setVendor(vendorDto);
		expectedOrder.setFoodQuantities(Arrays.asList(foodQuantityDto));
		UsersDto userDto = new UsersDto();
		userDto.setUserName("saniya");
		expectedOrder.setUserDto(userDto);
		expectedOrders.add(expectedOrder);

		List<OrdersDto> actualOrders = ordersService.purchaseHistory(userId, filterType);

		assertEquals(expectedOrders.size(), actualOrders.size());
		assertEquals(expectedOrders.get(0).getOrderDate(), actualOrders.get(0).getOrderDate());
	}

	@Test
	public void testPurchaseHistoryMonth() {
		int userId = 1;
		Users user = new Users();
		user.setUserId(1);
		Optional<Users> userOptional = Optional.of(user);
		Mockito.when(userService.findById(1)).thenReturn(userOptional);
		String filterType = "month";
		List<Orders> orders = new ArrayList<>();
		Orders order = new Orders();
		order.setOrderDate(LocalDate.now());
		FoodQuantity foodQuantity = new FoodQuantity();
		foodQuantity.setFoodQuantityId(1);
		Vendor vendor = new Vendor();
		vendor.setVendorId(1);
		foodQuantity.setVendor(vendor);
		order.setFoodQuantities(Arrays.asList(foodQuantity));
		
		order.setUser(user);
		orders.add(order);
		Mockito.when(ordersRepository.findByUserMonth(userId)).thenReturn(orders);

		List<OrdersDto> expectedOrders = new ArrayList<>();
		OrdersDto expectedOrder = new OrdersDto();
		expectedOrder.setOrderDate(LocalDate.now());
		FoodQuantityDto foodQuantityDto = new FoodQuantityDto();
		foodQuantityDto.setFoodId(1);
		VendorDto vendorDto = new VendorDto();
		vendorDto.setVendorName("munni");
		foodQuantityDto.setVendor(vendorDto);
		expectedOrder.setFoodQuantities(Arrays.asList(foodQuantityDto));
		UsersDto userDto = new UsersDto();
		userDto.setUserName("zareena");
		expectedOrder.setUserDto(userDto);
		expectedOrders.add(expectedOrder);

		List<OrdersDto> actualOrders = ordersService.purchaseHistory(userId, filterType);

		assertEquals(expectedOrders.size(), actualOrders.size());
		assertEquals(expectedOrders.get(0).getOrderDate(), actualOrders.get(0).getOrderDate());
	}
@Test
	public void testOrderWithInvalidUser() {

		Mockito.when(ordersRepository.findByUserMonth(Mockito.anyInt())).thenReturn(null);

		List<FoodQuantityDto> foodQuantityDtos = new ArrayList<>();
		FoodQuantityDto foodQuantityDto = new FoodQuantityDto();
		foodQuantityDto.setFoodId(1);
		foodQuantityDto.setQuantity(2);
		foodQuantityDtos.add(foodQuantityDto);

		NoSuchUserExists exception = assertThrows(NoSuchUserExists.class, () -> {
			ordersService.purchaseHistory(1, "week");
		});
		assertEquals("User with user Id:1 dose not exists", exception.getMessage());
	}

}
