package com.example.Payment_Gatway_demo.Repo;

import com.example.Payment_Gatway_demo.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepo extends JpaRepository<Merchant,Long> {
}
