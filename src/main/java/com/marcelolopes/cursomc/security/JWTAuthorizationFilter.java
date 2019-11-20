package com.marcelolopes.cursomc.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{
	
	JWTUtil jwtUtil;
	
	UserDetailsService userDetailsService;	

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;

	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest req,
		HttpServletResponse res,
		FilterChain chain) throws IOException, ServletException {
		
		String header = req.getHeader("Authorization");
		
		if (null != header && header.startsWith("Bearer ")) {
			UsernamePasswordAuthenticationToken token = getAuthentication(header.substring(7));
			if (null != token) {
				SecurityContextHolder.getContext().setAuthentication(token);
			}
		}
		chain.doFilter(req, res);
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if (jwtUtil.tokenValido(token)) {
			String userName = jwtUtil.getUseranme(token);
			UserDetails user = userDetailsService.loadUserByUsername(userName);
			return new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities()); 
		}
		return null;
	}

}
