package org.training.foodorderapplication.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
import org.training.foodorderapplication.service.UserService;
import org.training.foodorderapplication.service.VendorService;


@ExtendWith(SpringExtension.class)
public class OrderServiceImplTest {


	@InjectMocks
	private OrdersServiceImpl orderService;
	
	@Mock
	private VendorService vendorService;

	@Mock
	private FoodItemService foodItemService;

	@Mock
	private OrdersRepository ordersRepository;

	

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
		foodQuantityDto.setVendorDto(vendorDto);
		expectedOrder.setFoodQuantities(Arrays.asList(foodQuantityDto));
		UsersDto userDto = new UsersDto();
		userDto.setUserName("saniya");
		expectedOrder.setUserDto(userDto);
		expectedOrders.add(expectedOrder);

		List<OrdersDto> actualOrders = orderService.purchaseHistory(userId, filterType);

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
		foodQuantityDto.setVendorDto(vendorDto);
		expectedOrder.setFoodQuantities(Arrays.asList(foodQuantityDto));
		UsersDto userDto = new UsersDto();
		userDto.setUserName("zareena");
		expectedOrder.setUserDto(userDto);
		expectedOrders.add(expectedOrder);

		List<OrdersDto> actualOrders = orderService.purchaseHistory(userId, filterType);

		assertEquals(expectedOrders.size(), actualOrders.size());
		assertEquals(expectedOrders.get(0).getOrderDate(), actualOrders.get(0).getOrderDate());
	}

	@Test
	public void testOrderSearchWithInvalidUser() {

		Mockito.when(ordersRepository.findByUserMonth(Mockito.anyInt())).thenReturn(null);

		List<FoodQuantityDto> foodQuantityDtos = new ArrayList<>();
		FoodQuantityDto foodQuantityDto = new FoodQuantityDto();
		foodQuantityDto.setFoodId(1);
		foodQuantityDto.setQuantity(2);
		foodQuantityDtos.add(foodQuantityDto);

		NoSuchUserExists exception = assertThrows(NoSuchUserExists.class, () -> {
			orderService.purchaseHistory(1, "week");
		});
		assertEquals("User with user Id:1 dose not exists", exception.getMessage());
	}

	@Test
	public void testPurchaseHistoryWithNoOrderHistory() {
		int userId = 1;
		String filterType = "week";
		Users user = new Users();
		user.setUserId(userId);
		user.setUserName("Ameen");
		user.setUserEmail("ameen@example.com");
		Optional<Users> optionalUser = Optional.of(user);
		Mockito.when(userService.findById(userId)).thenReturn(optionalUser);
		List<Orders> orders = new ArrayList<>();
		assertThrows(NoOrderHistoryAvailable.class, () -> {
			orderService.purchaseHistory(1, "week");
			Mockito.when(ordersRepository.findByUserWeek(userId)).thenReturn(orders);
			orderService.purchaseHistory(userId, filterType);
		});
	}

	@Test
	void testOrderInvalidVendor() {

		List<FoodQuantityDtoCreate> quantityDtos = new ArrayList<>();
		quantityDtos.add(new FoodQuantityDtoCreate(1, 1, 1));
		Optional<Users> user = Optional.of(new Users());
		Mockito.when(userService.findById(1)).thenReturn(user);
		Optional<Vendor> vendor = Optional.empty();
		Mockito.when(vendorService.findById(1)).thenReturn(vendor);

		NoSuchVendorExists exception = assertThrows(NoSuchVendorExists.class,
				() -> orderService.order(1, quantityDtos));
		assertEquals("Vendor with vendor Id: 1 is not present", exception.getMessage());

	}

	@Test
	public void testOrderWithInvalidUser() {
		int invalidUserId = 999;
		List<FoodQuantityDtoCreate> foodQuantities = new ArrayList<>();

		NoSuchUserExists exception = assertThrows(NoSuchUserExists.class, () -> {
			orderService.order(invalidUserId, foodQuantities);
		});
		assertEquals("User with user Id:999 dose not exists", exception.getMessage());
	}

	@Test
	void testOrderWhenVendorDoesNotExist() {
		int userId = 1;
		List<FoodQuantityDtoCreate> quantityDtos = Arrays.asList(new FoodQuantityDtoCreate(1, 12345, 2),
				new FoodQuantityDtoCreate(2, 2, 3));

		Vendor vendor = new Vendor();
		vendor.setVendorId(2);
		vendor.setVendorName("Vendor2");
		List<Vendor> vendors = Arrays.asList(vendor);

		FoodItem foodItem1 = new FoodItem();
		foodItem1.setFoodItemId(1);
		foodItem1.setFoodItemName("Food1");
		foodItem1.setPrice((float) 10.0);
		foodItem1.setQuantity(10);
		foodItem1.setVendors(vendors);

		Mockito.when(userService.findById(userId)).thenReturn(Optional.of(new Users()));
		Mockito.when(vendorService.findAll()).thenReturn(Collections.singletonList(vendor));
		Mockito.when(foodItemService.findByFoodItemIdAndVendors(Mockito.anySet(), Mockito.anyCollection()))
				.thenReturn(Optional.of(foodItem1));

		NoSuchVendorExists exception = assertThrows(NoSuchVendorExists.class,
				() -> orderService.order(userId, quantityDtos));

		assertEquals("Vendor with vendor Id: 12345 is not present", exception.getMessage());
		Mockito.verify(userService, Mockito.times(1)).findById(userId);
		Mockito.verify(vendorService, Mockito.times(1)).findAll();
		Mockito.verify(foodItemService, Mockito.times(1)).findByFoodItemIdAndVendors(Mockito.anySet(),
				Mockito.anyCollection());
		Mockito.verifyNoMoreInteractions(userService, vendorService, foodItemService, ordersRepository);
	}

	@Test
	public void testOrderNonExistingFoodItemExceptionThrown() {
		
		int userId = 1;
		List<FoodQuantityDtoCreate> quantityDtos = Arrays.asList(new FoodQuantityDtoCreate(123, 1, 1));

		Vendor vendor = new Vendor();
		vendor.setVendorId(1);
		vendor.setVendorName("Vendor");

		Mockito.when(userService.findById(userId)).thenReturn(Optional.of(new Users()));
		Mockito.when(vendorService.findAll()).thenReturn(Arrays.asList(vendor));
		Mockito.when(foodItemService.findByFoodItemIdAndVendors(Mockito.anySet(), Mockito.anyCollection()))
				.thenReturn(Optional.empty());

		
		NoSuchFoodExists exception = Assertions.assertThrows(NoSuchFoodExists.class, () -> {
			orderService.order(userId, quantityDtos);
		});

		Assertions.assertEquals("Food with food Id: 123 is not present for vendor Id: 1", exception.getMessage());
	}

	@Test
	public void testOrderPlacedSuccessfully() {
		
		int userId = 1;
		List<FoodQuantityDtoCreate> quantityDtos = new ArrayList<>();
		quantityDtos.add(new FoodQuantityDtoCreate(1, 1, 2));

		Users user = new Users();
		user.setUserId(userId);

		Vendor vendor1 = new Vendor();
		vendor1.setVendorId(1);

		Vendor vendor2 = new Vendor();
		vendor2.setVendorId(2);

		FoodItem foodItem1 = new FoodItem();
		foodItem1.setFoodItemId(1);
		foodItem1.setQuantity(10);
		foodItem1.setVendors(Arrays.asList(vendor1));

		FoodItem foodItem2 = new FoodItem();
		foodItem2.setFoodItemId(2);
		foodItem2.setQuantity(20);
		foodItem2.setVendors(Arrays.asList(vendor2));

		Mockito.when(userService.findById(userId)).thenReturn(Optional.of(user));

		Mockito.when(vendorService.findAll()).thenReturn(Arrays.asList(vendor1, vendor2));

		Mockito.when(foodItemService.findByFoodItemIdAndVendors(any(), any())).thenReturn(Optional.of(foodItem1));

		ResponseDto responseDto = orderService.order(userId, quantityDtos);

		assertNotNull(responseDto);
		assertEquals(200, responseDto.getResponseCode());
		assertEquals("Order placed Successfully", responseDto.getResponseMessage().get(0));
	}

	@Test
	public void testOrderInsufficientFoodQuantity() {
		int userId = 1;
		List<FoodQuantityDtoCreate> quantityDtos = new ArrayList<>();
		quantityDtos.add(new FoodQuantityDtoCreate(1, 1, 20));

		Users user = new Users();
		user.setUserId(userId);

		Vendor vendor1 = new Vendor();
		vendor1.setVendorId(1);

		Vendor vendor2 = new Vendor();
		vendor2.setVendorId(2);

		FoodItem foodItem1 = new FoodItem();
		foodItem1.setFoodItemId(1);
		foodItem1.setQuantity(10);
		foodItem1.setVendors(Arrays.asList(vendor1));

		Mockito.when(userService.findById(userId)).thenReturn(Optional.of(user));

		Mockito.when(vendorService.findAll()).thenReturn(Arrays.asList(vendor1, vendor2));

		Mockito.when(foodItemService.findByFoodItemIdAndVendors(any(), any())).thenReturn(Optional.of(foodItem1));

		ResponseDto responseDto = orderService.order(userId, quantityDtos);

		assertNotNull(responseDto);
		assertEquals(200, responseDto.getResponseCode());
		assertEquals("Food with food Id:1 has only 10 quantities", responseDto.getResponseMessage().get(0));

	}

}
