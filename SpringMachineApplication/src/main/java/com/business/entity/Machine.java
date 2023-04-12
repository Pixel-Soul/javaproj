package com.business.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	private int machineid;
	
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
