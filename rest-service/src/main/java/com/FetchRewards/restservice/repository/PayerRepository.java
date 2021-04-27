package com.FetchRewards.restservice.repository;

import com.FetchRewards.restservice.entity.Payer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PayerRepository extends JpaRepository<Payer,Long> {
    Payer findByPayer(String payer);
}
