package com.example.demo.model;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name="organisation",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"registrationnumber"})
    })

public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    @Column(nullable=false)
    private String registrationnumber;
    
    @OneToMany(mappedBy="organisation")
    @JsonManagedReference
    private List<User> users; 

 
  

}
