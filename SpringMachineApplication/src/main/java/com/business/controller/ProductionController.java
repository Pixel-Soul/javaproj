package com.business.controller;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.business.entity.Production;
import com.business.repository.ProductionRepository;

/**
 * Controller di supporto
 * 
 */
@RestController
public class ProductionController {

	@Autowired
	private ProductionRepository repository;
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping("/factorydata/{location}")
	public Iterable<Production> getLocation(@PathVariable String location) throws ServletException {
		return repository.findByLocation(location);
	}
}
