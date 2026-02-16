
package com.example.demo.controller;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.OnboardingRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.User;
import com.example.demo.security.JWTUtil;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
	 private final UserService userService;
	 
	    private JWTUtil jwt;

	    public AuthController(UserService userService,JWTUtil jwt){
	        this.userService = userService;
	        this.jwt=jwt;
	    }	
	    
	    @PostMapping("/login")
	    public LoginResponse login(@RequestBody LoginRequest credentials){	           	
	        String email=credentials.getEmail();
	        String password=credentials.getPassword();
	        User user=userService.login(email, password);	      
	        String token=jwt.generateToken( user.getId(), 
	        	    user.getRole(), 
	        	    user.getOrganization() != null ? user.getOrganization().getId() : null);	      
	        return new LoginResponse(token,new UserResponse(user));
	    }
	    
	    @PostMapping("/signup")
	    public UserResponse login(@RequestBody OnboardingRequest onb){	           	
	        User user=userService.signUp(onb);	      
//	        String token=jwt.generateToken(user.getId(),user.getRole());	      
	        return new UserResponse(user);
	    }
	   
}







