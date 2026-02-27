package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndOrganisation_Id(String email,Long orgId);
    List<User> findAllByOrganisation_Id(Long id);
    Optional<User> findByIdAndOrganisation_Id(Long id,Long orgId);
}
