package org.training.foodorderapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.foodorderapplication.entity.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {

	List<Vendor> findAllByVendorIdIn(List<Integer> vendorIds);

}
