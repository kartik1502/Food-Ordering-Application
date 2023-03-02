package org.training.foodorderapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.training.foodorderapplication.dto.FoodQuantityDto;
import org.training.foodorderapplication.dto.ResponseDto;
import org.training.foodorderapplication.service.OrdersService;

@RestController
@RequestMapping("/users/{userId}/orders")
public class OrdersController {

	@Autowired
	private OrdersService service;

	@PostMapping
	public ResponseEntity<ResponseDto> order(@PathVariable int userId,
			@RequestBody List<FoodQuantityDto> quantityDtos) {

		return new ResponseEntity<>(service.order(userId, quantityDtos), HttpStatus.OK);
	}
}
