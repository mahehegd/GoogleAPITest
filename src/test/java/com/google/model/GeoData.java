package com.google.model;

import com.google.validator.Validator;

public class GeoData {
	
	public String address;
	
	@Validator
	public Double lattitude;
	
	@Validator
	public Double longitude;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLattitude() {
		return lattitude;
	}

	public void setLattitude(Double lattitude) {
		this.lattitude = lattitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public GeoData(String address, Double lattitude, Double longitude) {
		this.address = address;
		this.lattitude = lattitude;
		this.longitude = longitude;
	}
}
