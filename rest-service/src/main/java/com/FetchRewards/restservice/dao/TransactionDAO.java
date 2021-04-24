package com.FetchRewards.restservice.dao;

import com.FetchRewards.restservice.entity.Transaction;

import java.util.List;

public interface TransactionDAO {

    public void addTransaction(Transaction transaction);
    public List<Transaction> fetchAllTransactions();


}
