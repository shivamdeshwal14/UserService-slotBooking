package com.example.demo.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.OnboardingRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.model.Organisation;
import com.example.demo.model.User;
import com.example.demo.repository.OrganisationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JWTUtil;
import io.jsonwebtoken.Claims;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class AdminService {
	
	 	private final UserRepository userRepository;
	    private final PasswordEncoder passwordEncoder;
	    private final OrganisationRepository orp;
	    private final JWTUtil jwt;
	    
	    
	    public UserResponse onboard(OnboardingRequest onb,HttpServletRequest request) {
	    	 String authHeader = request.getHeader("Authorization");
 	        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
 	            throw new RuntimeException("Missing or invalid Authorization header");
 	        }
 	        String token = authHeader.substring(7); // remove "Bearer "
 	        Claims claims = jwt.validateToken(token);
 	        System.out.println("Claims: " + claims);
 	        Number orgIdNum = (Number) claims.get("orgId");
 	        Long orgId = orgIdNum.longValue();
	    	
 	        
	    	userRepository.findByEmail(onb.getEmail()).ifPresent(u->{
	    		throw new UserAlreadyExistsException("user already exists with this email,please login!");
	    	});
	    	
	    	Organisation org=orp.findById(orgId).orElseThrow(() -> new RuntimeException("Organization not found"));
	    
	         User user=new User();
	    	 user.setName(onb.getName());
	         user.setEmail(onb.getEmail().trim());
	         user.setPhone(onb.getPhone());
	         user.setPassword(passwordEncoder.encode(onb.getPassword()));
	     
	         user.setRole(onb.getRole()==null ?User.Role.DOCTOR :onb.getRole());
	         user.setActive(true);
	         user.setOrganization(org);
	         
	         return new UserResponse(userRepository.save(user));
	    }


	    
	    public UserResponse changeActiveStatus(long id,HttpServletRequest request) {
	    	
	    	
	    	String authHeader=request.getHeader("Authorization");
    		if(authHeader==null || !authHeader.startsWith("Bearer ")) throw new RuntimeException("Invalid or missing token");
    		String token=authHeader.substring(7);
    		Claims claims=jwt.validateToken(token);
    		Number orgIdNum = (Number) claims.get("orgId");
	        Long orgId = orgIdNum.longValue();
	    	User user=userRepository.findByIdAndOrganization_Id(id,orgId).orElseThrow(()->new RuntimeException("User not found with this id"));    			    	
	    	boolean isActive=user.isActive();
	    	user.setActive(!isActive);
	    	User updatedUser=userRepository.save(user);
	    	return new UserResponse(updatedUser);
	    }  
	    
	    public List<UserResponse> getAllEmp(HttpServletRequest request){
	    	
	    	String authHeader=request.getHeader("Authorization");
    		if(authHeader==null || !authHeader.startsWith("Bearer ")) throw new RuntimeException("Invalid or missing token");
    		String token=authHeader.substring(7);
    		Claims claims=jwt.validateToken(token);
    		Number orgIdNum = (Number) claims.get("orgId");
	        Long orgId = orgIdNum.longValue();
	        
	    	List<User> userdata=userRepository.findAllByOrganization_Id(orgId);
	    	return userdata.
	    			stream().
	    			map(UserResponse::new).toList();
	    }
}
