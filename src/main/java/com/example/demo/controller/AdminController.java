package com.example.demo.controller;
import com.example.demo.dto.OnboardingRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	
		 private final AdminService adminService;
	 
	 
	    @PostMapping("/onboard")
	    public UserResponse OnboardEmp(@RequestBody OnboardingRequest onb,HttpServletRequest request) {
	    	 return adminService.onboard(onb, request);
	    	 
	    }
	       	    
	    @GetMapping("/org/showEmps/")
	    public List<UserResponse> getAllUsers(HttpServletRequest request){
    	        return adminService.getAllEmp(request);  		
	    }
	   	    
	    @PatchMapping("/changeActiveStatus/user/{id}")
	    public UserResponse deactivateUser(@PathVariable long id,HttpServletRequest request) {

	    	return adminService.changeActiveStatus(id,request);	
	    	
	    }
	    
	   	}

	


