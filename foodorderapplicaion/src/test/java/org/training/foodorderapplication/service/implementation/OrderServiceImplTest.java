package org.training.foodorderapplication.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
	private OrdersServiceImpl ordersService;

	@Mock
	private OrdersRepository ordersRepository;

	@Mock
	private UserService userService;

	@Mock
	private VendorService vendorService;

	@Mock
	private FoodItemService foodItemService;

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
		foodQuantityDto.setVendorDto(vendorDto);
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
	void testOrderSuccess() {
		Users user = new Users();
		user.setUserId(1);
		Optional<Users> userOptional = Optional.of(user);
		Mockito.when(userService.findById(1)).thenReturn(userOptional);
		Vendor vendor = new Vendor();
		vendor.setVendorId(1);
		Optional<Vendor> vendorOptional = Optional.of(vendor);
		Mockito.when(vendorService.findById(1)).thenReturn(vendorOptional);
		List<Vendor> vendors = new ArrayList<>();
		vendors.add(vendor);

		FoodItem foodItem = new FoodItem();
		foodItem.setFoodItemId(1);
		foodItem.setQuantity(10);
		foodItem.setVendors(vendors);
		Optional<FoodItem> foodItemOptional = Optional.of(foodItem);
		Mockito.when(foodItemService.findByFoodItemIdAndVendors(1, vendor)).thenReturn(foodItemOptional);

		Mockito.when(ordersRepository.save(Mockito.any())).thenReturn(new Orders());

		List<FoodQuantityDtoCreate> quantityDtos = new ArrayList<>();
		FoodQuantityDtoCreate foodQuantityDto = new FoodQuantityDtoCreate();
		foodQuantityDto.setVendorId(1);
		foodQuantityDto.setFoodId(1);
		foodQuantityDto.setQuantity(5);
		quantityDtos.add(foodQuantityDto);
		ResponseDto responseDto = ordersService.order(1, quantityDtos);

		assertNotNull(responseDto);
		assertEquals(200, responseDto.getResponseCode());
		assertEquals("Order placed Successfully", responseDto.getResponseMessage().get(0));
	}

	@Test
	public void testOrderWithSearchInvalidUser() {

		Mockito.when(ordersRepository.findByUserMonth(Mockito.anyInt())).thenReturn(null);

		List<FoodQuantityDtoCreate> foodQuantityDtos = new ArrayList<>();
		FoodQuantityDtoCreate foodQuantityDto = new FoodQuantityDtoCreate();
		foodQuantityDto.setVendorId(1);
		foodQuantityDto.setFoodId(1);
		foodQuantityDto.setQuantity(2);
		foodQuantityDtos.add(foodQuantityDto);
		
		NoSuchUserExists exception = assertThrows(NoSuchUserExists.class, () -> {
			ordersService.purchaseHistory(1, "week");
		});
		assertEquals("User with user Id:1 dose not exists", exception.getMessage());
	}

	@Test
	public void testOrderWithInvalidUser() {

		Optional<Users> optionalUser = Optional.empty();
		Mockito.when(userService.findById(Mockito.anyInt())).thenReturn(optionalUser);

		List<FoodQuantityDtoCreate> foodQuantityDtos = new ArrayList<>();
		FoodQuantityDtoCreate foodQuantityDto = new FoodQuantityDtoCreate();
		foodQuantityDto.setVendorId(1);
		foodQuantityDto.setFoodId(1);
		foodQuantityDto.setQuantity(2);
		foodQuantityDtos.add(foodQuantityDto);
		NoSuchUserExists exception = assertThrows(NoSuchUserExists.class, () -> {

			ordersService.order(1, foodQuantityDtos);
		});
		assertEquals("User with user Id:1 dose not exists", exception.getMessage());

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
				() -> ordersService.order(1, quantityDtos));
		assertEquals("Vendor with vendor Id: 1 is not present", exception.getMessage());

	}

	@Test
	public void testOrderWithMultipleItems() {

		Users user = new Users();
		user.setUserId(1);
		user.setUserName("John");

		Vendor vendor = new Vendor();
		vendor.setVendorId(1);
		vendor.setCompanyName("Pizza Hut");
		List<Vendor> vendors = Arrays.asList(vendor);

		FoodItem foodItem1 = new FoodItem();
		foodItem1.setFoodItemId(1);
		foodItem1.setFoodItemName("Margherita Pizza");
		foodItem1.setPrice(10);
		foodItem1.setQuantity(5);
		foodItem1.setVendors(vendors);

		FoodItem foodItem2 = new FoodItem();
		foodItem2.setFoodItemId(2);
		foodItem2.setFoodItemName("Pepperoni Pizza");
		foodItem2.setPrice(12);
		foodItem2.setQuantity(10);
		foodItem2.setVendors(vendors);

		FoodQuantityDtoCreate foodQuantityDto1 = new FoodQuantityDtoCreate();
		foodQuantityDto1.setFoodId(1);
		foodQuantityDto1.setVendorId(1);
		foodQuantityDto1.setQuantity(2);

		FoodQuantityDtoCreate foodQuantityDto2 = new FoodQuantityDtoCreate();
		foodQuantityDto2.setFoodId(2);
		foodQuantityDto2.setVendorId(1);
		foodQuantityDto2.setQuantity(3);

		List<FoodQuantityDtoCreate> quantityDtos = new ArrayList<>();
		quantityDtos.add(foodQuantityDto1);
		quantityDtos.add(foodQuantityDto2);

		Mockito.when(userService.findById(1)).thenReturn(Optional.of(user));
		Mockito.when(vendorService.findById(1)).thenReturn(Optional.of(vendor));
		Mockito.when(foodItemService.findByFoodItemIdAndVendors(1, vendor)).thenReturn(Optional.of(foodItem1));
		Mockito.when(foodItemService.findByFoodItemIdAndVendors(2, vendor)).thenReturn(Optional.of(foodItem2));
		Mockito.when(foodItemService.save(foodItem1)).thenReturn(foodItem1);
		Mockito.when(foodItemService.save(foodItem2)).thenReturn(foodItem2);

		ResponseDto responseDto = ordersService.order(1, quantityDtos);

		assertNotNull(responseDto);
		assertEquals(200, responseDto.getResponseCode());
		assertEquals(2, quantityDtos.size());
		assertEquals("Order placed Successfully", responseDto.getResponseMessage().get(0));
		assertEquals(3, foodItem1.getQuantity());
		assertEquals(7, foodItem2.getQuantity());
	}

	@Test
	void testResponsesIsEmpty() {

		int userId = 1;
		List<FoodQuantityDtoCreate> quantityDtos = new ArrayList<>();
		FoodQuantityDtoCreate foodQuantityDto = new FoodQuantityDtoCreate();
		foodQuantityDto.setVendorId(1);
		foodQuantityDto.setFoodId(1);
		foodQuantityDto.setQuantity(1);
		quantityDtos.add(foodQuantityDto);
		Mockito.when(userService.findById(userId)).thenReturn(Optional.of(new Users()));
		Mockito.when(vendorService.findById(1)).thenReturn(Optional.of(new Vendor()));
		Mockito.when(foodItemService.findByFoodItemIdAndVendors(1, new Vendor())).thenReturn(Optional.empty());

		NoSuchFoodExists exception = assertThrows(NoSuchFoodExists.class, () -> {
			ordersService.order(userId, quantityDtos);
		});
		
	}

	@Test
	public void testOrder() {

		Users user = new Users();
		user.setUserId(1);
		user.setUserName("John");

		Vendor vendor = new Vendor();
		vendor.setVendorId(1);
		vendor.setCompanyName("Pizza Hut");
		List<Vendor> vendors = Arrays.asList(vendor);

		FoodItem foodItem1 = new FoodItem();
		foodItem1.setFoodItemId(1);
		foodItem1.setFoodItemName("Margherita Pizza");
		foodItem1.setPrice(10);
		foodItem1.setQuantity(5);
		foodItem1.setVendors(vendors);

		FoodItem foodItem2 = new FoodItem();
		foodItem2.setFoodItemId(2);
		foodItem2.setFoodItemName("Pepperoni Pizza");
		foodItem2.setPrice(12);
		foodItem2.setQuantity(10);
		foodItem2.setVendors(vendors);

		FoodQuantityDtoCreate foodQuantityDto1 = new FoodQuantityDtoCreate();
		foodQuantityDto1.setFoodId(1);
		foodQuantityDto1.setVendorId(1);
		foodQuantityDto1.setQuantity(20);

		FoodQuantityDtoCreate foodQuantityDto2 = new FoodQuantityDtoCreate();
		foodQuantityDto2.setFoodId(2);
		foodQuantityDto2.setVendorId(1);
		foodQuantityDto2.setQuantity(3);

		List<FoodQuantityDtoCreate> quantityDtos = new ArrayList<>();
		quantityDtos.add(foodQuantityDto1);
		quantityDtos.add(foodQuantityDto2);

		Mockito.when(userService.findById(1)).thenReturn(Optional.of(user));
		Mockito.when(vendorService.findById(1)).thenReturn(Optional.of(vendor));
		Mockito.when(foodItemService.findByFoodItemIdAndVendors(1, vendor)).thenReturn(Optional.of(foodItem1));
		Mockito.when(foodItemService.findByFoodItemIdAndVendors(2, vendor)).thenReturn(Optional.of(foodItem2));
		Mockito.when(foodItemService.save(foodItem1)).thenReturn(foodItem1);
		Mockito.when(foodItemService.save(foodItem2)).thenReturn(foodItem2);

		List<String> responses = new ArrayList<>();
		responses.add("Food with food Id:1 has only 5 quantities");
		assertEquals(new ResponseDto(responses, 200), ordersService.order(1, quantityDtos));
	}
}
