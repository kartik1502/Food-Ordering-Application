package org.training.foodorderapplication.serviceImplTest;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.training.foodorderapplication.dto.FoodItemDto;
import org.training.foodorderapplication.entity.FoodItem;
import org.training.foodorderapplication.entity.Vendor;
import org.training.foodorderapplication.exception.NoSearchDataException;
import org.training.foodorderapplication.repository.FoodItemRepository;
import org.training.foodorderapplication.repository.VendorRepository;
import org.training.foodorderapplication.service.implementation.FoodItemServiceImpl;

@ExtendWith(SpringExtension.class)
public class FoodItemServiceImplTest {

	@Mock
	private FoodItemRepository itemRepository;

	@Mock
	private VendorRepository vendorRepository;

	@InjectMocks
	private FoodItemServiceImpl foodItemService;

	@Test
	public void testFoodItemWithMultipleResults() {

		String foodVendorName = "divya";
		List<FoodItem> item = new ArrayList<>();
		FoodItem foodItem1 = new FoodItem();
		foodItem1.setItemName("coffee");
		foodItem1.setItemId(1);
		FoodItem foodItem2 = new FoodItem();
		foodItem2.setItemName("tea");
		foodItem2.setItemId(2);
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

		when(itemRepository.findByItemNameContains(foodVendorName)).thenReturn(item);
		when(vendorRepository.findByVendorName(foodVendorName)).thenReturn(vendor1);
		when(itemRepository.findByVendors(vendor1)).thenReturn(item);
		List<FoodItemDto> result = foodItemService.getFoodVendorName(foodVendorName);
		Assertions.assertEquals(2, result.size());
		Assertions.assertEquals("coffee", result.get(0).getItemName());
		Assertions.assertEquals("divya", result.get(0).getVendorDtos().get(0).getVendorName());
		Assertions.assertEquals("priya", result.get(0).getVendorDtos().get(1).getVendorName());
		Assertions.assertEquals("tea", result.get(1).getItemName());

	}

	@Test
	void testSearch()

	{

		String foodVendorName = "divya";
		List<FoodItem> item = new ArrayList<>();
		FoodItem foodItem1 = new FoodItem();
		foodItem1.setItemName("coffee");
		foodItem1.setItemId(1);
		FoodItem foodItem2 = new FoodItem();
		foodItem2.setItemName("tea");
		foodItem2.setItemId(2);
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

		when(itemRepository.findByItemNameContains(foodVendorName)).thenReturn(item);
		when(vendorRepository.findByVendorName(foodVendorName)).thenReturn(vendor1);
		when(itemRepository.findByVendors(vendor1)).thenReturn(item);
		assertThrows(NoSearchDataException.class, () ->

		foodItemService.getFoodVendorName("geetha"));
	}

	@Test
	public void testFoodItemWithOneResult() {

		String foodVendorName = "divya";
		List<FoodItem> item = new ArrayList<>();
		FoodItem foodItem1 = new FoodItem();
		foodItem1.setItemName("coffee");
		foodItem1.setItemId(1);
		item.add(foodItem1);
		Vendor vendor1 = new Vendor();
		vendor1.setVendorId(1);
		vendor1.setVendorName("divya");
		List<Vendor> vendor = Arrays.asList(vendor1);
		foodItem1.setVendors(vendor);
		when(itemRepository.findByItemNameContains(foodVendorName)).thenReturn(item);
		when(vendorRepository.findByVendorName(foodVendorName)).thenReturn(vendor1);
		when(itemRepository.findByVendors(vendor1)).thenReturn(item);

		List<FoodItemDto> result = foodItemService.getFoodVendorName(foodVendorName);

		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals("coffee", result.get(0).getItemName());
		Assertions.assertEquals("divya", result.get(0).getVendorDtos().get(0).getVendorName());

	}

}
