package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.OnboardingRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.exception.AccountDisabledException;
import com.example.demo.exception.InvalidCredentialsException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.model.User;
import com.example.demo.model.User.Role;

import com.example.demo.repository.UserRepository;
import com.example.demo.security.JWTUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
	
	 	private final UserRepository userRepository;
	    private final PasswordEncoder passwordEncoder;
	    private final JWTUtil jwt;
	 	      
	    
	       public LoginResponse login(LoginRequest credentials) {
	    	   
		    	String email=credentials.getEmail();
			    String password=credentials.getPassword();    
			    
		       	User user= userRepository.findByEmail(email.trim()).orElseThrow(InvalidCredentialsException::new);
		       	
	//	       	if(!passwordEncoder.matches(password,user.getPassword())) throw new InvalidCredentialsException();
		       	if(!user.isActive()) throw new AccountDisabledException();
		       	
	//	       	user logged in successfully now generate token
		        String token=jwt.generateToken( user.getId(), user.getRole(),user.getOrganisation() != null ? user.getOrganisation().getId():null);
		    
		        return new LoginResponse(token,new UserResponse(user));
	     
	       }
	       
	       
	       public UserResponse signUp(OnboardingRequest onb) {
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

	            return new UserResponse(userRepository.save(user));
	         
	       }
	       
	       
	       
}
