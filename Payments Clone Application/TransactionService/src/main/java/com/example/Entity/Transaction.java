package com.example.Entity;

import com.example.TxnStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,unique =true)
    private String txnId;

    @Column(nullable=false)
    private Long fromUserId;

    @Column(nullable=false)
    private Long toUserId;

    @Column(nullable=false)
    private Double amount;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private TxnStatus status;

    private String reason;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private OffsetDateTime dateCreated;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;


}
