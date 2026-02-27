package com.example.demo.service;

import com.example.demo.model.Organisation;
import com.example.demo.repository.OrganisationRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final OrganisationRepository orp;
    
    public List<Organisation> getAllOrganizations() {
            return orp.findAll();
        }
    }




  
    

