package com.example.demo.controller;

import com.example.demo.dto.OnboardingRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.User;

import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
	 private final UserService userService;
	
	    public AdminController(UserService userService){
	        this.userService = userService;
	       
	    }	   
	    @PostMapping("/onboard")
	    public UserResponse OnboardEmp(@RequestBody OnboardingRequest onb) {
	    	User  user=userService.onboard(onb);
	    	return new UserResponse(user);
	   
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

	


