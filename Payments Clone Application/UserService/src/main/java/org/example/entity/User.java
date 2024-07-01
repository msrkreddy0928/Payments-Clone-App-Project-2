package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false)
    private Long id;
    @Column(nullable=false,unique = true)

    private String name;

    @Column(nullable=false,unique = true)

    private String Email;

    @Column(nullable=false,unique = true)

    private String phone;

    @Column(nullable=false,unique = true)

    private String kycNumber;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)

    private OffsetDateTime dateCreated;

    @UpdateTimestamp
    @Column(nullable = false)

    private OffsetDateTime lastUpdated;
}
