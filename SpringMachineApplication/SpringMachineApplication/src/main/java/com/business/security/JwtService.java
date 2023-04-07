package com.business.security;


import java.security.Key;
import java.util.Date;
 
import javax.servlet.http.HttpServletRequest;
 
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
 
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;



/**
 * Gestione del token di autorizzazione e expiration time   
 *
 */
@Component
public class JwtService {
 
	 private static final long EXPIRATIONTIME = 86400000;
	 private static final String PREFIX = "Bearer";
	 private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
 
	 public String getToken(String username) {
 
	  String t = Jwts.builder()
	  .setSubject(username)
	  .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
	  .signWith(key)
	  .compact();
 
	  return t;
 
 }
 
 public String getAuthUser(HttpServletRequest request) {
 
	 String token = request.getHeader(HttpHeaders.AUTHORIZATION);
 
	  if (token != null) {
	   String user = Jwts
	   .parserBuilder()
	   .setSigningKey(key)
	   .build()
	   .parseClaimsJws(token.replace(PREFIX, ""))
	   .getBody()
	   .getSubject();
	   if (user!=null)
	    return user;
 
  }
  return null; 
 
 }
}