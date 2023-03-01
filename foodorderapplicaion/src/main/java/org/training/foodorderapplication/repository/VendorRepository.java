package org.training.foodorderapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.foodorderapplication.entity.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {

	Vendor findByVendorName(String foodVendorName);

}
