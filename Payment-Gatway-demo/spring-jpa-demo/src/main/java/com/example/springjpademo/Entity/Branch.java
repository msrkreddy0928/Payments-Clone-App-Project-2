package com.example.springjpademo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

   // @OneToMany(mappedBy ="branch",fetch=FetchType.EAGER)
    //@JsonIgnoreProperties("branch")
    @OneToMany(mappedBy ="branch")
    @JsonIgnoreProperties("branch")
    private Set<Employee>employeeSet;

}
