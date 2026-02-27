package com.example.demo.repository;

import com.example.demo.model.Organisation;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {
	
    Optional<Organisation> findByName(String name);
    
    Optional<Organisation>findByRegistrationnumber(String registrationnumber);
}
