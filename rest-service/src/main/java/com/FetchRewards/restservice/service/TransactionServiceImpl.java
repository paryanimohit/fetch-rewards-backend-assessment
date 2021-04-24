package com.FetchRewards.restservice.service;

import com.FetchRewards.restservice.dao.TransactionDAO;
import com.FetchRewards.restservice.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    private TransactionDAO transactionDAO;

    @Autowired
    public TransactionServiceImpl(TransactionDAO theTransactionDAO){
        transactionDAO = theTransactionDAO;
    }

    @Override
    @Transactional
    public List<Transaction> fetchAllTransactions(){
        return transactionDAO.fetchAllTransactions();
    }

    @Override
    @Transactional
    public void addTransaction(Transaction transaction) {
        transactionDAO.addTransaction(transaction);
    }
}
