package com.business.security;


import java.io.IOException;
 
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
 
import com.business.security.JwtService;
 

/**
 * Filtro per gestire le richieste al server
 * 
 * Controlla se l'origine della richiesta è autorizzata e nel caso chiede il login
 * In caso la richiesta non sia autorizzata solleverà un eccezione 
 *
 */
@Component
public class AuthenticationFilter extends OncePerRequestFilter{
 
	@Autowired
	private JwtService jwtService;
 
	@Override
	protected void doFilterInternal(HttpServletRequest request
			, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
 
 
		if(request.getRequestURI().equals("/login")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		
		//Filtro per consentire dashboard
		if(request.getRequestURI().equals("/dashboard.html")) {
			filterChain.doFilter(request, response);
			return;
		}
 
		String jws = request.getHeader(HttpHeaders.AUTHORIZATION);
 
		if(jws!=null) {
			String user = jwtService.getAuthUser(request);
			if(user!=null)
				filterChain.doFilter(request, response);
			return;
		}
 
		throw new ServletException("not authorized!");
	}
 
 
}