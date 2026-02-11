package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="users", uniqueConstraints= {@UniqueConstraint(columnNames= {"email"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN, DOCTOR, USER

    private boolean active = true;

   
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Role { ADMIN, DOCTOR, USER }

    // Default constructor
    public User() {}

    // Constructor with all fields except ID and createdAt
    public User(String name, String email, String phone, String password, Role role,
                String organization, String department) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        
    }

    
    // ---------------- Getters ----------------
    public Long getId() { return id; }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getPhone() { return phone; }

    public String getPassword() { return password; }

    public Role getRole() { return role; }

    public boolean isActive() { return active; }

    

    public LocalDateTime getCreatedAt() { return createdAt; }

    // ---------------- Setters ----------------
    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setEmail(String email) { this.email = email; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setPassword(String password) { this.password = password; }

    public void setRole(Role role) { this.role = role; }

    public void setActive(boolean active) { this.active = active; }

  

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
