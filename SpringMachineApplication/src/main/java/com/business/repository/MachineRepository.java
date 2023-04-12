package com.business.repository;

import org.springframework.data.repository.CrudRepository;

import com.business.entity.Machine;


/**
 * 
 * Repository per la classe Machine
 */
public interface MachineRepository extends CrudRepository<Machine, Integer> {

}
