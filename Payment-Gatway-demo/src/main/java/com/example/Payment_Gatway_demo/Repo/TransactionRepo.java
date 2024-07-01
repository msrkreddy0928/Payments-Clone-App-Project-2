package com.example.Payment_Gatway_demo.Repo;

import com.example.Payment_Gatway_demo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {
    Transaction findByTxnId(String txnId);
}
