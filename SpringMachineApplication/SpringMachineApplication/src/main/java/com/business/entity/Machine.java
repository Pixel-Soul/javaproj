package com.business.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Classe JPA che mappa le istanze di Machine sul DB
 * 
 */
@Entity
public class Machine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, name = "id")
	@NotBlank(message="Machine id is required")
	private Integer machineid;
	
	@Pattern(regexp = "[A-Z0-9]", message="Invalid format")
	@Size(max = 10, min = 10, message = "Must be 10")
	@Column(nullable = false, name = "name")
	private String machinename;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "machine", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Production> prod = new ArrayList<>();

	public Machine(String machinename) {
		this.machinename = machinename;
	}

	
	

	public Machine() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getMachineid() {
		return machineid;
	}



	public void setMachineid(Integer machineid) {
		this.machineid = machineid;
	}



	public String getMachinename() {
		return machinename;
	}

	public void setMachinename(String machinename) {
		this.machinename = machinename;
	}

	public List<Production> getProd() {
		return prod;
	}

	public void setProd(List<Production> prod) {
		this.prod = prod;
	}

	@Override
	public String toString() {
		return "Machine [machineid=" + machineid + ", machinename=" + machinename + ", prod=" + prod.size() + "]";
	}
	
	
}
