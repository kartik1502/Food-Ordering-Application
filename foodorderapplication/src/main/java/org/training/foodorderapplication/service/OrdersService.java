package org.training.foodorderapplication.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.training.foodorderapplication.dto.OrdersDto;

@Service
public interface OrdersService {

	List<OrdersDto> purchaseHistory(int userId, String filterType);
  
  ResponseDto order(int userId, List<FoodQuantityDto> quantityDtos);

}
