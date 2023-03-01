package org.training.foodorderapplication.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class VendorDto {

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@NotNull
	@Pattern(regexp = "[a-zA-Z ]+", message = "Enter a valid Vendor name")
	private String vendorName;

	@NotNull
	@Pattern(regexp = "[a-zA-Z ]+", message = "Enter a valid Vendor company name")
	private String companyName;

}
