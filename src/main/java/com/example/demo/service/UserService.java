package com.example.demo.service;

import com.example.demo.dto.OnboardingRequest;
import com.example.demo.exception.AccountDisabledException;
import com.example.demo.exception.InvalidCredentialsException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Organisation;
import com.example.demo.model.User;
import com.example.demo.model.User.Role;
import com.example.demo.repository.OrganisationRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrganisationRepository orp;
       public UserService(UserRepository userRepository,PasswordEncoder paswordEncoder, OrganisationRepository orp){
        this.userRepository = userRepository;
        this.passwordEncoder=paswordEncoder;
        this.orp=orp;
     
    }


   

    public User login(String email,String password) {
    	
    	User user= userRepository.findByEmail(email.trim()).orElseThrow(InvalidCredentialsException::new);
    	
//    	if(!passwordEncoder.matches(password,user.getPassword())) throw new InvalidCredentialsException();
    	if(!user.isActive()) throw new AccountDisabledException();
 
        return user;
    }
    
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
         user.setRole(User.Role.DOCTOR);
         user.setActive(true);
         user.setOrganization(org);
         
         return userRepository.save(user);
    }




    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
   
    public User getUserByIdService(Long id) {
    	User user=userRepository.findById(id).orElseThrow(()->new UserNotFoundException("user doestnot exists"));
    	
    	return user;
    }
    
    public User deleteUserByAccountService(Long id) {
    	User user=userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User Doesnot exist"));
    	
    	userRepository.deleteById(id);
    	return user;
    	
    }
    
    public User changeActiveStatusService(long id) {
    	User user=userRepository.findById(id)
    			.orElseThrow(()->new UserNotFoundException("User does Not exists"));
    	boolean isActive=user.isActive();
    	System.out.println("isActive"+isActive);
    	user.setActive(!isActive);
    	return userRepository.save(user);
    }




  
    
}
