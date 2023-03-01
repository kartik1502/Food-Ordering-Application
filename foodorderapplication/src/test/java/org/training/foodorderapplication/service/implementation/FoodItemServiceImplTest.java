package org.training.foodorderapplication.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.training.foodorderapplication.dto.FoodItemDto;
import org.training.foodorderapplication.dto.VendorDto;
import org.training.foodorderapplication.entity.FoodItem;
import org.training.foodorderapplication.entity.Vendor;
import org.training.foodorderapplication.exception.NoSearchDataException;
import org.training.foodorderapplication.repository.FoodItemRepository;
import org.training.foodorderapplication.service.implementation.FoodItemServiceImpl;
import org.training.foodorderapplication.service.implementation.VendorServiceImpl;

@ExtendWith(SpringExtension.class)
public class FoodItemServiceImplTest {

	@Mock
	private FoodItemRepository foodItemRepository;

	@Mock
	private VendorServiceImpl vendorService;

	@InjectMocks
	private FoodItemServiceImpl foodItemService;

	@Test
	public void testFoodItemWithMultipleResults() {

		String foodVendorName = "divya";
		List<FoodItem> item = new ArrayList<>();
		FoodItem foodItem1 = new FoodItem();
		foodItem1.setFoodItemName("coffee");
		foodItem1.setFoodItemId(1);
		FoodItem foodItem2 = new FoodItem();
		foodItem2.setFoodItemName("tea");
		foodItem2.setFoodItemId(2);
		item.add(foodItem1);
		item.add(foodItem2);
		Vendor vendor1 = new Vendor();
		vendor1.setVendorId(1);
		vendor1.setVendorName("divya");
		Vendor vendor2 = new Vendor();
		vendor2.setVendorId(2);
		vendor2.setVendorName("priya");

		List<Vendor> vendor = Arrays.asList(vendor1, vendor2);
		foodItem1.setVendors(vendor);

		foodItem2.setVendors(vendor);

		when(foodItemRepository.findByFoodItemNameContainingIgnoreCase(foodVendorName)).thenReturn(item);
		when(vendorService.findByVendorNameContainingIgnoreCase(foodVendorName)).thenReturn(vendor);
		when(foodItemRepository.findByVendors(vendor1)).thenReturn(item);
		List<FoodItemDto> result = foodItemService.getFoodVendorName(foodVendorName);
		Assertions.assertEquals(2, result.size());
		Assertions.assertEquals("coffee", result.get(0).getFoodItemName());
		Assertions.assertEquals("divya", result.get(0).getVendorDtos().get(0).getVendorName());
		Assertions.assertEquals("priya", result.get(0).getVendorDtos().get(1).getVendorName());
		Assertions.assertEquals("tea", result.get(1).getFoodItemName());

	}

	@Test
	void testSearch()

	{

		String foodVendorName = "divya";
		Vendor vendor1 = new Vendor();
		vendor1.setVendorId(1);
		vendor1.setVendorName("divya");
		Vendor vendor2 = new Vendor();
		vendor2.setVendorId(2);
		vendor2.setVendorName("priya");

		List<Vendor> vendor = Arrays.asList(vendor1, vendor2);

		List<FoodItem> item = new ArrayList<>();
		FoodItem foodItem1 = new FoodItem();
		foodItem1.setFoodItemName("coffee");
		foodItem1.setFoodItemId(1);
		FoodItem foodItem2 = new FoodItem();
		foodItem2.setFoodItemName("tea");
		foodItem2.setFoodItemId(2);
		foodItem1.setVendors(vendor);

		foodItem2.setVendors(vendor);
		item.add(foodItem1);
		item.add(foodItem2);

		when(foodItemRepository.findByFoodItemNameContainingIgnoreCase(foodVendorName)).thenReturn(item);
		when(vendorService.findByVendorNameContainingIgnoreCase(foodVendorName)).thenReturn(vendor);
		when(foodItemRepository.findByVendors(vendor1)).thenReturn(item);
		assertThrows(NoSearchDataException.class, () ->

		foodItemService.getFoodVendorName("geetha"));
	}

	@Test
	public void testFoodItemWithOneResult() {

		String foodVendorName = "divya";
		List<FoodItem> item = new ArrayList<>();
		FoodItem foodItem1 = new FoodItem();
		foodItem1.setFoodItemName("coffee");
		foodItem1.setFoodItemId(1);
		item.add(foodItem1);
		Vendor vendor1 = new Vendor();
		vendor1.setVendorId(1);
		vendor1.setVendorName("divya");

		List<Vendor> vendor = Arrays.asList(vendor1);
		foodItem1.setVendors(vendor);
		when(foodItemRepository.findByFoodItemNameContainingIgnoreCase(foodVendorName)).thenReturn(item);
		when(vendorService.findByVendorNameContainingIgnoreCase(foodVendorName)).thenReturn(vendor);
		when(foodItemRepository.findByVendors(vendor1)).thenReturn(item);

		List<FoodItemDto> result = foodItemService.getFoodVendorName(foodVendorName);

		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals("coffee", result.get(0).getFoodItemName());
		Assertions.assertEquals("divya", result.get(0).getVendorDtos().get(0).getVendorName());

	}

	@Test
	void testFoodVendorName() {
		String foodVendorName = "divya";

		// Mock data
		List<FoodItem> foodItems = new ArrayList<>();
		FoodItem foodItem = new FoodItem();
		foodItem.setFoodItemId(1);
		foodItem.setFoodItemName("coffee");
		Vendor vendor = new Vendor();
		vendor.setVendorId(1);
		vendor.setVendorName("priya");
		foodItem.setVendors(List.of(vendor));
		foodItems.add(foodItem);
		when(foodItemRepository.findByFoodItemNameContainingIgnoreCase(foodVendorName)).thenReturn(foodItems);

		// Test
		List<FoodItemDto> foodItemDtos = foodItemService.getFoodVendorName(foodVendorName);

		// Verify results
		Assertions.assertNotNull(foodItemDtos);
		Assertions.assertEquals(foodItems.size(), foodItemDtos.size());

		FoodItemDto foodItemDto = foodItemDtos.get(0);

		Assertions.assertEquals(foodItem.getFoodItemName(), foodItemDto.getFoodItemName());

		List<VendorDto> vendorDtos = foodItemDto.getVendorDtos();
		Assertions.assertNotNull(vendorDtos);
		Assertions.assertEquals(foodItem.getVendors().size(), vendorDtos.size());

		VendorDto vendorDto = vendorDtos.get(0);

		Assertions.assertEquals(vendor.getVendorName(), vendorDto.getVendorName());
	}

	@Test
	void testGetFoodVendorName() {
		String foodVendorName = "pizza";

		Vendor vendor1 = new Vendor();
		vendor1.setVendorId(1);
		vendor1.setVendorName("divya");
		Vendor vendor2 = new Vendor();
		vendor2.setVendorId(2);
		vendor2.setVendorName("priya");

		List<Vendor> vendor = Arrays.asList(vendor1, vendor2);

		List<FoodItem> item = new ArrayList<>();
		FoodItem foodItem1 = new FoodItem();
		foodItem1.setFoodItemName("coffee");
		foodItem1.setFoodItemId(1);
		FoodItem foodItem2 = new FoodItem();
		foodItem2.setFoodItemName("tea");
		foodItem2.setFoodItemId(2);
		foodItem1.setVendors(vendor);

		foodItem2.setVendors(vendor);
		item.add(foodItem1);
		item.add(foodItem2);

		when(foodItemRepository.findByFoodItemNameContainingIgnoreCase(foodVendorName))
				.thenReturn(Arrays.asList(foodItem1));
		when(vendorService.findByVendorNameContainingIgnoreCase(foodVendorName)).thenReturn(Arrays.asList(vendor2));
		when(foodItemRepository.findByVendorsIn(Arrays.asList(vendor2))).thenReturn(Arrays.asList(foodItem1));

		List<VendorDto> expectedVendorDtos = new ArrayList<>();
		VendorDto vendorDto1 = new VendorDto();
		BeanUtils.copyProperties(vendor1, vendorDto1, "vendorId");
		expectedVendorDtos.add(vendorDto1);
		VendorDto vendorDto2 = new VendorDto();
		BeanUtils.copyProperties(vendor2, vendorDto2, "vendorId");
		expectedVendorDtos.add(vendorDto2);

		FoodItemDto expectedFoodItemDto = new FoodItemDto();
		BeanUtils.copyProperties(foodItem1, expectedFoodItemDto, "foodItemId");
		expectedFoodItemDto.setVendorDtos(expectedVendorDtos);

		List<FoodItemDto> expectedFoodItemDtos = Arrays.asList(expectedFoodItemDto);

		List<FoodItemDto> actualFoodItemDtos = foodItemService.getFoodVendorName(foodVendorName);

		assertEquals(expectedFoodItemDtos, actualFoodItemDtos);
	}

	@Test
	public void testGetVendorName() {

		List<Vendor> vendorList = new ArrayList<>();
		Vendor vendor = new Vendor();
		vendor.setVendorId(1);
		vendor.setVendorName("Pizza House");
		vendorList.add(vendor);
		List<FoodItem> foodItemList = new ArrayList<>();
		FoodItem foodItem = new FoodItem();
		foodItem.setFoodItemId(1);
		foodItem.setFoodItemName("Pizza");
		foodItem.setVendors(vendorList);
		foodItemList.add(foodItem);
		when(foodItemRepository.findByFoodItemNameContainingIgnoreCase("Pizza")).thenReturn(foodItemList);
		when(vendorService.findByVendorNameContainingIgnoreCase("Pizza")).thenReturn(vendorList);
		when(foodItemRepository.findByVendorsIn(vendorList)).thenReturn(foodItemList);
		List<FoodItemDto> foodItemDtos = foodItemService.getFoodVendorName("Pizza");
		assertEquals(foodItemDtos.size(), 1);
		assertEquals(foodItemDtos.get(0).getFoodItemName(), "Pizza");
		assertEquals(foodItemDtos.get(0).getVendorDtos().size(), 1);
		assertEquals(foodItemDtos.get(0).getVendorDtos().get(0).getVendorName(), "Pizza House");
	}

}
