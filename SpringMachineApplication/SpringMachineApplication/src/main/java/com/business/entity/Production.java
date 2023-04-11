package com.business.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Classe JPA che mappa le istanze di Production sul DB
 * 
 */
@Entity
public class Production {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, name = "id")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "machine")
	private Machine machine;
	
	@NotBlank(message="Location is required")
	@Column(nullable = false, name="location")
	private String location;
	
	@Column(nullable = false, name="hoursofday")
	@Size(max = 0, min = 23, message = "Error on hour format")
	private int hourOfDay;
	
	@Min(value = 0, message="Min make error")
	@Column(nullable = false, name="productionNumber")
	private int make;

	public Production(Machine machine, String location, int hourOfDay, int make) {
		this.machine = machine;
		this.location = location;
		this.hourOfDay = hourOfDay;
		this.make = make;
	}
	
	

	public Production() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getHourOfDay() {
		return hourOfDay;
	}

	public void setHourOfDay(int hourOfDay) {
		this.hourOfDay = hourOfDay;
	}

	public int getMake() {
		return make;
	}

	public void setMake(int make) {
		this.make = make;
	}

	@Override
	public String toString() {
		return "Production [machine=" + machine.getMachinename() + ", location=" + location + ", hourOfDay=" + hourOfDay + ", make="
				+ make + "]";
	}
	
	
	
	
	
}
