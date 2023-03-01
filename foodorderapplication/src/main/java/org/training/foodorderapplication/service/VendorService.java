package org.training.foodorderapplication.service;

import java.util.List;
import java.util.Optional;

import org.training.foodorderapplication.entity.Vendor;

public interface VendorService {

	Optional<Vendor> findById(int vendorId);

	List<Vendor> findAllByVendorIdIn(List<Integer> vendorIds);

	List<Vendor> findAll();

}
