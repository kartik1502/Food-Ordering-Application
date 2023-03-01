package org.training.foodorderapplication.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.foodorderapplication.entity.Vendor;
import org.training.foodorderapplication.repository.VendorRepository;
import org.training.foodorderapplication.service.VendorService;

@Service
public class VendorServiceImpl implements VendorService {

	@Autowired
	private VendorRepository repository;

	@Override
	public Optional<Vendor> findById(int vendorId) {
		return repository.findById(vendorId);
	}

	@Override
	public List<Vendor> findAllByVendorIdIn(List<Integer> vendorIds) {
		return repository.findAllByVendorIdIn(vendorIds);
	}

	@Override
	public List<Vendor> findAll() {
		return repository.findAll();
	}

}
