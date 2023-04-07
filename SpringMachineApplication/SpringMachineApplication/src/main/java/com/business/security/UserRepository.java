package com.business.security;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository per la classe User
 *
 */

@RepositoryRestResource(exported = false)
public interface UserRepository extends CrudRepository<User, Long>{

	Optional<User> findByUsername(String username);
} 