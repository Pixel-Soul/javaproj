package com.business.security;


import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
 
/**
 * 
 * Classe di supporto per autorizzazione login
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
 @Autowired
 private UserRepository repository;
 
 @Override
 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
 
   Optional<com.business.security.User> user = repository.findByUsername(username);
 
   UserBuilder builder;
   if (user.isPresent()) {
 
	   com.business.security.User currentUser = user.get();
	   
    builder = User.withUsername(username).password(currentUser.getPassword()).roles(currentUser.getRole());
 
   } else
    throw new UsernameNotFoundException("User not found!");
 
   return builder.build(); 
 }
 
}