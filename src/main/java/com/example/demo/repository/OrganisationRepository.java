package com.example.demo.repository;

import com.example.demo.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

    // Optional: find by name
    Organisation findByName(String name);
}
