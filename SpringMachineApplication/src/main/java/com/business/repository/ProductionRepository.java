package com.business.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import com.business.entity.Production;


/**
 * 
 * Repository per la classe Production
 */
@RepositoryRestResource
public interface ProductionRepository extends CrudRepository<Production, Integer> {
	
	
	public List<Production> findByLocation(String location);

}
