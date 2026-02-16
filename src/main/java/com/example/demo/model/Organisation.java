package com.example.demo.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="organization")
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;

    @OneToMany(mappedBy="organization")
    private List<User> users;  // doctors/admins

    // Constructors
    public Organisation() {}

    public Organisation(String name, String location) {
        this.name = name;
        this.location = location;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }
}
