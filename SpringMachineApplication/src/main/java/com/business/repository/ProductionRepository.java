package com.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.business.entity.Machine;
import com.business.entity.Production;


/**
 * 
 * Repository per la classe Production
 */
@RepositoryRestResource(exported = false)
public interface ProductionRepository extends CrudRepository<Production, Integer> {
	

}
