package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.model.User.Role;

public class OnboardingRequest {
	String name;
	String email;
	String password;
	String phone;
	Role role;
	 private LocalDateTime createdAt = LocalDateTime.now();
   Long id;
	
	boolean active=true;
	
	public OnboardingRequest(String name,	String email,	String password,String phone,Role role) {
		this.name=name;
		this.role=role;
		this.email=email;
		this.phone=phone;
		this.password=password;
		
	}
	
	public Long getId() { return id; }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getPhone() { return phone; }

    public String getPassword() { return password; }

    public Role getRole() { return role; }

    public boolean isActive() { return active; }



    public LocalDateTime getCreatedAt() { return createdAt; }

    // ---------------- Setters ----------------
    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setEmail(String email) { this.email = email; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setPassword(String password) { this.password = password; }

    public void setRole(Role role) { this.role = role; }

    public void setActive(boolean active) { this.active = active; }



}
