package com.FetchRewards.restservice.repository;

import com.FetchRewards.restservice.entity.Payer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayerRepository extends JpaRepository<Payer, Long> {

}
