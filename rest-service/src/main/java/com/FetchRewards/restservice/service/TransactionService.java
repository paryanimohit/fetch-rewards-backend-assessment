package com.FetchRewards.restservice.service;

import com.FetchRewards.restservice.entity.Transaction;

import java.util.List;

public interface TransactionService {

    public List<Transaction> fetchAllTransactions();
    public void addTransaction(Transaction transaction);
}
