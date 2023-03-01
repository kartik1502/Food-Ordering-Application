package org.training.foodorderapplication.controller;

import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.training.foodorderapplication.dto.FoodItemDto;
import org.training.foodorderapplication.service.FoodItemService;

@RestController
@RequestMapping("/")
public class FoodItemController {

	@Autowired
	private FoodItemService foodItemService;

	@GetMapping("/items")
	public ResponseEntity<List<FoodItemDto>> getFoodVendorName(@RequestParam @Size(min=3) String foodVendorName) {
		return new ResponseEntity<>(foodItemService.getFoodVendorName(foodVendorName), HttpStatus.OK);
	}

}
