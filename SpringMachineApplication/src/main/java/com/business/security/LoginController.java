package com.business.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
 
import com.business.security.AccountCredetials;
import com.business.security.JwtService;


/**
 * Gestione del login e delle credenziali  
 *
 */
@RestController
public class LoginController {
 
	@Autowired
	private AuthenticationManager am;
 
	@Autowired
	private JwtService jwtservice;
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ResponseEntity<?> getToken(@RequestBody AccountCredetials credentials) {
 
		var r = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
		Authentication auth = am.authenticate(r);
		String jwts = jwtservice.getToken(auth.getName());
		return ResponseEntity.ok()
					  .header(HttpHeaders.AUTHORIZATION,"Bearer "+ jwts)
					  .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
					  .build();
 
	}
}