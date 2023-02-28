package org.training.foodorderapplication.service;

import org.training.foodorderapplication.entity.Vendor;

public interface VendorService {

	Vendor findByVendorName(String foodVendorName);

}
