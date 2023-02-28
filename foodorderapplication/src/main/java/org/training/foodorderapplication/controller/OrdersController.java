package org.training.foodorderapplication.controller;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.training.foodorderapplication.dto.OrdersDto;
import org.training.foodorderapplication.repository.OrdersRepository;
import org.training.foodorderapplication.service.OrdersService;

@RestController
public class OrdersController {
	@Autowired
	OrdersRepository ordersRepository;

	@Autowired
	OrdersService ordersService;

	@GetMapping("/users/{userId}/orders")
	public ResponseEntity<List<OrdersDto>> purchaseHistory(@RequestParam int userId,
			@RequestParam @Pattern(regexp = "^(week|month)$", message = "Should be either week or month") String filterType) {
		return new ResponseEntity<>(ordersService.purchaseHistory(userId, filterType), HttpStatus.OK);

	}
}