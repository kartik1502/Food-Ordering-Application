package org.training.foodorderapplication.service;

import java.util.Optional;

import org.training.foodorderapplication.entity.Vendor;

public interface VendorService {

	Optional<Vendor> findById(int vendorId);

}
