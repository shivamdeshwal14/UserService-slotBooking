package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrganisationResponse;

import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor

@RestController
@RequestMapping("/api/user")
public class UserController {
	private final UserService use;
	
@GetMapping("/all/orgnizations")
	public List<OrganisationResponse> getAllOrganizations(){
    	return use.getAllOrganizations()
    			.stream()
    			.map(org->{
    				 OrganisationResponse dto=new OrganisationResponse();
        			 dto.setName(org.getName());
        			 dto.setOrgId(org.getId());
        			 dto.setLocation(org.getLocation());
        			 return dto;
    			})
    			.toList();
}
}
