package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter

@RequiredArgsConstructor
@Entity

@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "organisation_id"})
    }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private String email;

    private String phone;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name="organisation_id")
    @JsonBackReference
    private Organisation organisation; 

    public enum Role { ADMIN, DOCTOR, USER }

   
    public User(String name, String email, String phone, String password, Role role, Organisation organisation) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.organisation = organisation;
    }
}
