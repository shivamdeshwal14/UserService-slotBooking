
package com.example.demo.controller;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.OnboardingRequest;
import com.example.demo.dto.UserResponse;

import com.example.demo.service.AuthService;


import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	  
	
	    private final AuthService authService;

	    
	    @PostMapping("/login")
	    public LoginResponse login(@RequestBody LoginRequest credentials){	           	
	        return authService.login(credentials);	  
	    }	   
	    
	    @PostMapping("/signup")
	    public UserResponse login(@RequestBody OnboardingRequest onb){	           	
	        return authService.signUp(onb);	     	      
	       
	    }
	   
}







