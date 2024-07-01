package com.example.springjpademo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(unique = true)

    private  String email;

    @OneToOne(cascade=CascadeType.ALL)
    private Address address;

    @ManyToOne
    @JsonIgnoreProperties("employeeSet")
    private Branch branch;

   // @ManyToOne
    //@JsonIgnoreProperties("employeeSet")
    //private Branch branch;

}
