package com.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestMapping;

import com.business.entity.Machine;
import com.business.entity.Production;


/**
 * 
 * Repository per la classe Production
 */
@RepositoryRestResource
public interface ProductionRepository extends CrudRepository<Production, Integer> {
	
	
	public List<Production> findByLocation(String location);

}
