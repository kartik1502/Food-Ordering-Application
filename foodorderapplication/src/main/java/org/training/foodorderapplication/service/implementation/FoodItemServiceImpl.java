package org.training.foodorderapplication.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.foodorderapplication.dto.FoodItemDto;
import org.training.foodorderapplication.dto.VendorDto;
import org.training.foodorderapplication.entity.FoodItem;
import org.training.foodorderapplication.entity.Vendor;
import org.training.foodorderapplication.exception.NoSearchDataException;
import org.training.foodorderapplication.repository.FoodItemRepository;
import org.training.foodorderapplication.service.FoodItemService;
import org.training.foodorderapplication.service.VendorService;

@Service
public class FoodItemServiceImpl implements FoodItemService {

	@Autowired
	private FoodItemRepository foodItemRepository;

	@Autowired
	private VendorService vendorService;
	
	
	Logger logger = LoggerFactory.getLogger(FoodItemServiceImpl.class);
	

	@Override
	public List<FoodItemDto> getFoodVendorName(String foodVendorName) {

	    List<FoodItem> foodItem = foodItemRepository.findByFoodItemNameContainingIgnoreCase(foodVendorName);
	    List<FoodItemDto> foodItemDtos = new ArrayList<>();
	    if (foodItem.isEmpty()) {
	        List<Vendor> vendors = vendorService.findByVendorNameContainingIgnoreCase(foodVendorName);
	        List<FoodItem> items = foodItemRepository.findByVendorsIn(vendors);
	       
	    	   items.forEach(e -> {
	       
	            List<VendorDto> vendorDtos = new ArrayList<>();
	            e.getVendors().forEach(v -> {
	                VendorDto vendorDto = new VendorDto();
	                if(v.getVendorName().toLowerCase().contains(foodVendorName.toLowerCase())) {
	                    BeanUtils.copyProperties(v, vendorDto, "vendorId");
	                    vendorDtos.add(vendorDto);
	                }
	            });
	            FoodItemDto foodItemDto = new FoodItemDto();
	            BeanUtils.copyProperties(e, foodItemDto, "foodItemId");
	            foodItemDto.setVendorDtos(vendorDtos);
	            foodItemDtos.add(foodItemDto);
	        });
	    
	    }
	        foodItem.forEach(e -> {
	            List<VendorDto> vendorDtos = new ArrayList<>();
	            e.getVendors().forEach(v -> {
	                VendorDto vendorDto = new VendorDto();
	                BeanUtils.copyProperties(v, vendorDto, "vendorId");
	                vendorDtos.add(vendorDto);
	            });
	            FoodItemDto foodItemDto = new FoodItemDto();
	            BeanUtils.copyProperties(e, foodItemDto, "foodItemId");
	            foodItemDto.setVendorDtos(vendorDtos);
	            foodItemDtos.add(foodItemDto);
	        });
	    
	    if (foodItemDtos.isEmpty()) {
	        logger.error("No such fooditem or vendor exists exception handled");
	        throw new NoSearchDataException();
	    }
	    logger.info("Data Displayed");
	    return foodItemDtos;
	}



}
