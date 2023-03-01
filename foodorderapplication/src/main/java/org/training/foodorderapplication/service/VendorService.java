package org.training.foodorderapplication.service;

import java.util.List;

import org.training.foodorderapplication.entity.Vendor;

public interface VendorService {

	List<Vendor> findByVendorNameContainingIgnoreCase(String foodVendorName);

}
