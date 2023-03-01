package org.training.foodorderapplication.service.implementation;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.training.foodorderapplication.entity.FoodItem;
import org.training.foodorderapplication.entity.Vendor;
import org.training.foodorderapplication.repository.FoodItemRepository;
import org.training.foodorderapplication.service.FoodItemService;



import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.training.foodorderapplication.dto.FoodItemDto;
import org.training.foodorderapplication.dto.VendorDto;
import org.training.foodorderapplication.exception.NoSearchDataException;
import org.training.foodorderapplication.repository.VendorRepository;
import org.training.foodorderapplication.service.VendorService;

@Service
public class FoodItemServiceImpl implements FoodItemService {
	
	@Autowired
	private FoodItemRepository repository;

	@Override
	public Optional<FoodItem> findById(int foodId) {
		return repository.findById(foodId);
	}

	@Override
	public FoodItem save(FoodItem foodItem) {
		return repository.save(foodItem);
	}

	@Override
	public Optional<FoodItem> findByFoodItemIdAndVendors(int foodId, Vendor vendor) {
		return repository.findByFoodItemIdAndVendors(foodId, vendor);
  }
  
  @Override
	public List<FoodItemDto> getFoodVendorName(String foodVendorName) {

		List<FoodItem> item = itemRepository.findByItemNameContains(foodVendorName);
		List<FoodItemDto> itemDtos = new ArrayList<>();
		if (item.isEmpty()) {
			Vendor vendors = vendorService.findByVendorName(foodVendorName);
			itemRepository.findByVendors(vendors).forEach(e -> {
				List<VendorDto> vendorDtos = new ArrayList<>();
				e.getVendors().forEach(v -> {
					VendorDto vendorDto = new VendorDto();
					if (v.getVendorName().equals(foodVendorName)) {
						BeanUtils.copyProperties(v, vendorDto, "vendorId");
						vendorDtos.add(vendorDto);
					}
				});
				FoodItemDto itemDto = new FoodItemDto();
				BeanUtils.copyProperties(e, itemDto, "itemId");
				itemDto.setVendorDtos(vendorDtos);
				itemDtos.add(itemDto);
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
			throw new NoSearchDataException();
		}
		return itemDtos;
    
	}
}

