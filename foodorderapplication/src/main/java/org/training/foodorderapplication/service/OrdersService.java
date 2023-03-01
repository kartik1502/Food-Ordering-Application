package org.training.foodorderapplication.service;

import java.util.List;

import org.training.foodorderapplication.dto.FoodQuantityDtoCreate;
import org.training.foodorderapplication.dto.OrdersDto;
import org.training.foodorderapplication.dto.ResponseDto;

public interface OrdersService {

	List<OrdersDto> purchaseHistory(int userId, String filterType);

	ResponseDto order(int userId, List<FoodQuantityDtoCreate> quantityDtos);

}
