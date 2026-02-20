package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.OnboardingRequest;
import com.example.demo.exception.AccountDisabledException;
import com.example.demo.exception.InvalidCredentialsException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.model.User;
import com.example.demo.model.User.Role;
import com.example.demo.repository.OrganisationRepository;
import com.example.demo.repository.UserRepository;

@Service
public class AuthService {
	 private final UserRepository userRepository;
	    private final PasswordEncoder passwordEncoder;
	    private final OrganisationRepository orp;
	       public AuthService(UserRepository userRepository,PasswordEncoder paswordEncoder, OrganisationRepository orp){
	        this.userRepository = userRepository;
	        this.passwordEncoder=paswordEncoder;
	        this.orp=orp;
	       }
	       
	       
//	       Login-Service
	       public User login(String email,String password) {
	       	
	       	User user= userRepository.findByEmail(email.trim()).orElseThrow(InvalidCredentialsException::new);
	       	
//	       	if(!passwordEncoder.matches(password,user.getPassword())) throw new InvalidCredentialsException();
	       	if(!user.isActive()) throw new AccountDisabledException();
	    
	           return user;
	       }
	       
//	       signup-Service
	       
	       public User signUp(OnboardingRequest onb) {
	       	userRepository.findByEmail(onb.getEmail()).ifPresent(u->{
	       		throw new UserAlreadyExistsException("user already exists with this email,please login!");
	       	});
	       	
	       	User user=new User();
	       	 user.setName(onb.getName());
	            user.setEmail(onb.getEmail().trim());
	            user.setPhone(onb.getPhone());
	            user.setPassword(passwordEncoder.encode(onb.getPassword()));
	            user.setActive(true);
	            user.setRole(Role.USER);

	            return userRepository.save(user);
	       }
	       
	       
	       
}
