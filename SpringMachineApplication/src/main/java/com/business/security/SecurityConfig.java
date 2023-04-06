package com.business.security;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
 
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
 
import com.business.security.UserDetailsServiceImpl;



/**
 * Configurazioni di sicurezza per il server
 * 
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {
 
	 @Autowired
	 private UserDetailsServiceImpl userDetailsService;
 
	 @Autowired
	 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 
	  System.out.println("in SecurityConfig - configureGlobal " + auth);
	  auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	 }
 
	 @Bean
	 public AuthenticationManager am(AuthenticationConfiguration ac) throws Exception {
		 return ac.getAuthenticationManager();
	 }
 
	 //turn-off automatic security
	 @Autowired
	 private AuthenticationFilter af;
	 @Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 http.authorizeHttpRequests((authz)-> authz
				 							.anyRequest()
				 							.permitAll()
				 							.and()
				 							.addFilterBefore(af, UsernamePasswordAuthenticationFilter.class))
		 									.csrf()
		 									.disable();
		 return http.build();
	 }
}
 
 
 
 
 
 
 