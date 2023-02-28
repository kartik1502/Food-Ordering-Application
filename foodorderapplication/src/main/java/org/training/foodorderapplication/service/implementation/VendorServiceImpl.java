package org.training.foodorderapplication.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.foodorderapplication.entity.Vendor;
import org.training.foodorderapplication.repository.VendorRepository;
import org.training.foodorderapplication.service.VendorService;

@Service
public class VendorServiceImpl implements VendorService {

	@Autowired
	private VendorRepository vendorRepository;

	@Override
	public Vendor findByVendorName(String foodVendorName) {
		return vendorRepository.findByVendorName(foodVendorName);
	}

}
