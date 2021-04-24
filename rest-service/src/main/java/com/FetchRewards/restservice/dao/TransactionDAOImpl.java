package com.FetchRewards.restservice.dao;

import com.FetchRewards.restservice.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TransactionDAOImpl implements TransactionDAO {

    private EntityManager entityManager;

    @Autowired
    public TransactionDAOImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        Transaction newTransaction = entityManager.merge(transaction);
        transaction.setId(newTransaction.getId());
    }

    @Override
    public List<Transaction> fetchAllTransactions() {
        Query theQuery = entityManager.createQuery("select payer,points,timeStamp from Transaction");
        List<Transaction> transaction =theQuery.getResultList();
        return transaction;
    }
}
