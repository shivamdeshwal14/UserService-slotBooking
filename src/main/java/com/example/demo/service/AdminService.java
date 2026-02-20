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

@Service
public class AdminService {
	 	private final UserRepository userRepository;
	    private final PasswordEncoder passwordEncoder;
	    private final OrganisationRepository orp;
	    public AdminService(UserRepository userRepository,PasswordEncoder paswordEncoder, OrganisationRepository orp){
	        this.userRepository = userRepository;
	        this.passwordEncoder=paswordEncoder;
	        this.orp=orp;
	     
	    }
	    
	    public User onboard(OnboardingRequest onb,Long orgId) {
	    	System.out.println("inside user service");
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
	         
	         return userRepository.save(user);
	    }


	    
	    public User changeActiveStatusService(long id,Long orgId) {
	    	User user=userRepository.findByIdAndOrganization_Id(id,orgId).orElseThrow(()->new RuntimeException("User not found with this id"));
	    	
	    			    	
	    	boolean isActive=user.isActive();
	       	
	    	user.setActive(!isActive);
	    	return userRepository.save(user);
	    }

	    
	    
	    public List<UserResponse> getAllEmpService(Long orgId){
	    	List<User> userdata=userRepository.findAllByOrganization_Id(orgId);
	    	return userdata.
	    			stream().
	    			map(UserResponse::new).toList();
	    }
}
