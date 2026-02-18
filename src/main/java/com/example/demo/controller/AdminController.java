package com.example.demo.controller;
import com.example.demo.dto.OnboardingRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.User;
import com.example.demo.security.JWTUtil;
import com.example.demo.service.UserService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
	 private final UserService userService;
	 private final JWTUtil jwt;

	 
	    public AdminController(UserService userService, JWTUtil jwt){
	        this.userService = userService;
			this.jwt = jwt;
			
	    }	   
	    @PostMapping("/onboard")
	    public UserResponse OnboardEmp(@RequestBody OnboardingRequest onb,HttpServletRequest request) {
	    	 try {
	    	    	    	        
	    	        String authHeader = request.getHeader("Authorization");
	    	        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
	    	            throw new RuntimeException("Missing or invalid Authorization header");
	    	        }
	    	        
	    	        String token = authHeader.substring(7); // remove "Bearer "
	    	        Claims claims = jwt.validateToken(token);
	    	        System.out.println("Claims: " + claims);
	    	        Number orgIdNum = (Number) claims.get("orgId");
	    	        Long orgId = orgIdNum.longValue();
	    	        System.out.println("orgId from token: " + orgId);
	    	        User user = userService.onboard(onb, orgId);
	    	        return new UserResponse(user);
	    	        
	    	    } catch (Exception e) {
	    	        e.printStackTrace();  
	    	        throw e;
	    	    }
	   
	    }
	    
	    
//	    get all employees of the organization admin and doctors both;
	    @GetMapping("/org/showEmps/")
	    public List<UserResponse> getAllUsers(HttpServletRequest request){
	    		System.out.println(" api hit successfully");
	    		String authHeader=request.getHeader("Authorization");
	    		if(authHeader==null || !authHeader.startsWith("Bearer ")) throw new RuntimeException("Invalid or missing token");
	    	
	    		String token=authHeader.substring(7);
	    		Claims claims=jwt.validateToken(token);
	    		
	    		Number orgIdNum = (Number) claims.get("orgId");
    	        Long orgId = orgIdNum.longValue();
    	        return userService.getAllEmpService(orgId);  		
	    }
	    
	    
	    
//	    make user account disable but it should be of their organization only not anyone else

	    
	    @PatchMapping("/changeActiveStatus/user/{id}")
	    public UserResponse deactivateUser(@PathVariable long id,HttpServletRequest request) {
	    	String authHeader=request.getHeader("Authorization");
    		if(authHeader==null || !authHeader.startsWith("Bearer ")) throw new RuntimeException("Invalid or missing token");
    		String token=authHeader.substring(7);
    		Claims claims=jwt.validateToken(token);
    		
    		Number orgIdNum = (Number) claims.get("orgId");
	        Long orgId = orgIdNum.longValue();
	    	User user=userService.changeActiveStatusService(id,orgId);	   
	    	
	    	return new UserResponse(user);
	    }
	    
	   	}

	


