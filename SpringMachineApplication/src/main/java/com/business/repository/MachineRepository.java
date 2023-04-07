package com.business.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestMapping;

import com.business.entity.Machine;


/**
 * 
 * Repository per la classe Machine
 */
public interface MachineRepository extends CrudRepository<Machine, Integer> {

}
