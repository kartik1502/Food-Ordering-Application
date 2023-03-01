package org.training.foodorderapplication.service;

import java.util.List;

import org.training.foodorderapplication.dto.OrdersDto;

public interface OrdersService {

	List<OrdersDto> purchaseHistory(int userId, String filterType);

}
