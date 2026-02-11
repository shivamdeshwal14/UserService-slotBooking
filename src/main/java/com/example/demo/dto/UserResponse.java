package com.example.demo.dto;

import com.example.demo.model.User;
import com.example.demo.model.User.Role;

public class UserResponse {

	private Long id;
	private String name;
	private String email;
	private Role role;
	private String phone;
	
	public UserResponse(User user) {
		this.id=user.getId();
		this.name=user.getName();
		this.email=user.getEmail();
		this.role=user.getRole();
		
		this.phone=user.getPhone();
		
		
	}
	public Long getId() {return id;}
	public String getName() {return name;}
	public Role getRole() {return role;}
	public String getEmail() {return email;}
	public String getPhone() {return phone;}
}

