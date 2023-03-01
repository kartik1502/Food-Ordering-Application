package org.training.foodorderapplication.service;

import java.util.List;

import org.training.foodorderapplication.dto.FoodQuantityDto;
import org.training.foodorderapplication.dto.ResponseDto;

public interface OrdersService {

	ResponseDto order(int userId, List<FoodQuantityDto> quantityDtos);

}
