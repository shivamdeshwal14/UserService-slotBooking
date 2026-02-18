package com.example.demo.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.model.User.Role;

import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

@Component
public class JWTAuthFilter  extends OncePerRequestFilter{
private final JWTUtil jwt;
public JWTAuthFilter(JWTUtil jwt) {
	this.jwt=jwt;
	System.out.println("✅ JWTAuthFilter bean created");
}
		  @Override
		 protected void doFilterInternal(
		            HttpServletRequest request,
		            HttpServletResponse response,
		            FilterChain filterChain
		    )throws ServletException ,IOException{
			  
	String authHeader=request.getHeader("Authorization");
//	 System.out.println(authHeader);
	
	if(authHeader==null ||!authHeader.startsWith("Bearer ")) {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return;
}
	String token=authHeader.substring(7);
//	System.out.println(token);
	try {
		System.out.println("Starting try block");
		Claims claims=jwt.validateToken(token);
		String roleStr=claims.get("role",String.class);
		System.out.println("role in token--- "+roleStr);
		
		Role role = Role.valueOf(roleStr);
		if(Role.ADMIN!=role) {
			System.out.println("if condition ");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		
		UsernamePasswordAuthenticationToken authentication =
	            new UsernamePasswordAuthenticationToken(
	                    claims.getSubject(), // userId
	                    null,
	                    List.of(new SimpleGrantedAuthority("ROLE_" + role.name()))
	            );

	    SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	catch(Exception e) {
		System.out.println("inside exception");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return;
	}
	
	filterChain.doFilter(request, response);
	
}
		  
@Override
protected boolean shouldNotFilter(HttpServletRequest req) {
	String path=req.getRequestURI();
	return path.startsWith("/auth/")|| path.startsWith("/api/user");
}

}
