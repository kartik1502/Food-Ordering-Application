package org.training.foodorderapplication.controller;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.training.foodorderapplication.dto.FoodQuantityDtoCreate;
import org.training.foodorderapplication.dto.OrdersDto;
import org.training.foodorderapplication.dto.ResponseDto;
import org.training.foodorderapplication.service.OrdersService;

@RestController
@RequestMapping
public class OrdersController {

	@Autowired
	private OrdersService service;

	@GetMapping("/users/{userId}/orders")
	public ResponseEntity<List<OrdersDto>> purchaseHistory(@RequestHeader(value="Users") int userId,
			@RequestParam @Pattern(regexp = "^([wW][eE]{2}[kK]|[mM][oO][nN][tT][hH])$", message = "Should be either week or month") String filterType) {
		return new ResponseEntity<>(service.purchaseHistory(userId, filterType), HttpStatus.OK);

	}
	
	@PostMapping("/users/{userId}/orders")
	public ResponseEntity<ResponseDto> order(@PathVariable int userId,
			@RequestBody List<FoodQuantityDtoCreate> quantityDtos) {

		return new ResponseEntity<>(service.order(userId, quantityDtos), HttpStatus.OK);
	}
}
	