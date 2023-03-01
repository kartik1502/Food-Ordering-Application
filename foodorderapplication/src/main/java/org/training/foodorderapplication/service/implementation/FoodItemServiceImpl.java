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
	private FoodItemRepository itemRepository;

	@Autowired
	private VendorService vendorService;
	
	
	Logger logger = LoggerFactory.getLogger(FoodItemServiceImpl.class);
	

	@Override
	public List<FoodItemDto> getFoodVendorName(String foodVendorName) {

		List<FoodItem> item = itemRepository.findByItemNameContainingIgnoreCase(foodVendorName);
		List<FoodItemDto> itemDtos = new ArrayList<>();
		if (item.isEmpty()) {
			List<Vendor> vendors = vendorService.findByVendorNameContainingIgnoreCase(foodVendorName);
			vendors.forEach(v1 -> {
				itemRepository.findByVendors(v1).forEach(e -> {
					List<VendorDto> vendorDtos = new ArrayList<>();
					e.getVendors().forEach(v -> {
						VendorDto vendorDto = new VendorDto();
						if (v.getVendorName().contains(foodVendorName.toLowerCase())) {
							BeanUtils.copyProperties(v, vendorDto, "vendorId");
							vendorDtos.add(vendorDto);
						}
					});
					FoodItemDto itemDto = new FoodItemDto();
					BeanUtils.copyProperties(e, itemDto, "itemId");
					itemDto.setVendorDtos(vendorDtos);
					itemDtos.add(itemDto);
				});
				
			});
			

		}
		item.forEach(e -> {
			List<VendorDto> vendorDtos = new ArrayList<>();
			e.getVendors().forEach(v -> {
				VendorDto vendorDto = new VendorDto();
				BeanUtils.copyProperties(v, vendorDto, "vendorId");
				vendorDtos.add(vendorDto);
			});
			FoodItemDto itemDto = new FoodItemDto();
			BeanUtils.copyProperties(e, itemDto, "itemId");
			itemDto.setVendorDtos(vendorDtos);
			itemDtos.add(itemDto);
		});
		if (itemDtos.isEmpty()) {
			logger.error("No such fooditem or vendor exists exception handled");
			throw new NoSearchDataException();
		}
		logger.info("Data Displayed");
		return itemDtos;
	}

}
