package com.example.demo.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
@Getter
@Setter
@Data
@Entity
@Table(name="organization")
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;

    @OneToMany(mappedBy="organization")
    @JsonManagedReference
    private List<User> users;  // doctors/admins

    // Constructors
    public Organisation() {}

    public Organisation(String name, String location) {
        this.name = name;
        this.location = location;
    }

}
