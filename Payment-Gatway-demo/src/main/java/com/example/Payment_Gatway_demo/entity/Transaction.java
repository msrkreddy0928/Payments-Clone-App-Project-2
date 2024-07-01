package com.example.Payment_Gatway_demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Setter
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String txnId;

    @Column(nullable = false)
    Long merchantId;



    @Column(nullable = false)
    Long userId;

    @Column(nullable = false)
    Double amount;

    @Column(nullable = false)
    private String status;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

}
