package org.training.foodorderapplication.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.training.foodorderapplication.entity.Vendor;
import org.training.foodorderapplication.repository.VendorRepository;
import org.training.foodorderapplication.service.implementation.VendorServiceImpl;

@ExtendWith(SpringExtension.class)
public class VendorserviceImplTest {
	
	
	@Mock
	private VendorRepository vendorRepository;

	@InjectMocks
	private VendorServiceImpl vendorService;

	@Test
    public void testFindByVendorNameContainingIgnoreCase() {
        String foodVendorName = "divya";
        Vendor vendor1 = new Vendor();
        vendor1.setVendorId(1);
        vendor1.setVendorName("divya");
        vendor1.setCompanyName("homza");
        Vendor vendor2 = new Vendor();
        vendor2.setVendorId(2);
        vendor2.setVendorName("priya");
        vendor2.setCompanyName("arabian");
        List<Vendor> expectedVendors = Arrays.asList(vendor1, vendor2);

        Mockito.when(vendorRepository.findByVendorNameContainingIgnoreCase(foodVendorName))
               .thenReturn(expectedVendors);

        List<Vendor> actualVendors = vendorService.findByVendorNameContainingIgnoreCase(foodVendorName);

        assertEquals(expectedVendors, actualVendors);
        Mockito.verify(vendorRepository).findByVendorNameContainingIgnoreCase(foodVendorName);
    }
	
	
	
}
