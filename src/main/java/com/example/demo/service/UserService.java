package com.example.demo.service;

import com.example.demo.model.Organisation;
import com.example.demo.repository.OrganisationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

   
    private final OrganisationRepository orp;
       public UserService(OrganisationRepository orp){
     
        this.orp=orp;
     
    }
       
    public List<Organisation> getAllOrganizations() {
            return orp.findAll();
        }
    }




  
    

