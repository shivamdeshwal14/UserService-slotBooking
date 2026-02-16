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
	    	        System.out.println("inside /onboard");
	    	        
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
	    	        e.printStackTrace();  // See the exact exception
	    	        throw e;
	    	    }
	   
	    }
	    @GetMapping("/allusers")
	    public List<UserResponse> getAllUsers(){
	    	return userService.getAllUsers()
	    			.stream()
	    			.map(UserResponse::new)
	    			.toList();
	    }
	    
//	    fetch user with the id
	    
	    @GetMapping("/user/{id}")
	    public UserResponse getUserById(@PathVariable long id) {;
	    	User user=userService.getUserByIdService(id);
	    	return new UserResponse(user);
	    }
	    
//	    delete user with the id
	    
	    @DeleteMapping("delete/user/{id}")
	    public String deleteUserAccount(@PathVariable long id) {
	    		User user=userService.deleteUserByAccountService(id);
	    		String str="user with id-"+id+" and email-"+user.getEmail()+" deleted successfully";
	    		return str;
	    		}
	    
	    @PatchMapping("/changeActiveStatus/user/{id}")
	    public UserResponse deactivateUser(@PathVariable long id) {
	    	User user=userService.changeActiveStatusService(id);	    			
	    	return new UserResponse(user);
	    }
	    
	   	}

	


