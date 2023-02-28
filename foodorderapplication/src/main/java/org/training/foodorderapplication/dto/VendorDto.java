package org.training.foodorderapplication.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class VendorDto {

	@NotNull
	@Pattern(regexp = "[a-zA-Z ]+", message = "Enter a valid Vendor name")
	private String vendorName;

	@NotNull
	@Pattern(regexp = "[a-zA-Z ]+", message = "Enter a valid Vendor company name")
	private String companyName;

}
