package com.business.entity;

import javax.validation.constraints.*;

public class ProductionForm {
	@NotBlank(message="Machine id is required")
	private String machineid;
	
	@Pattern(regexp = "^[A-Z0-9]{10}$", message="Invalid machine name")
	//@Size(max = 10, min = 10, message = "Lenght must be 10")
	private String machinename;
	
	@NotBlank(message="Location is required")
	private String location;
	
	@Min(value = 0, message="Make must be greater than or equals 0")
	private int make;

	public String getMachineid() {
		return machineid;
	}

	public void setMachineid(String machineid) {
		this.machineid = machineid;
	}

	public String getMachinename() {
		return machinename;
	}

	public void setMachinename(String machinename) {
		this.machinename = machinename;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getMake() {
		return make;
	}

	public void setMake(int make) {
		this.make = make;
	}

	@Override
	public String toString() {
		return "ProductionForm [machineid=" + machineid + ", machinename=" + machinename + ", location=" + location
				+ ", make=" + make + "]";
	}
	
	
	
	

}
