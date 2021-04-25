package com.FetchRewards.restservice.dao;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.FetchRewards.restservice.entity.Payer;
import com.FetchRewards.restservice.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
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
//        transaction.setId(newTransaction.getId());
        Payer newPayer = new Payer();
        //MAKE A CONDITION THAT IF PAYER ALREADY EXISTS IN DATABASE, ADD THE TOTAL POINTS. DO NOT MAKE NEW PAYER.
        newPayer.setPayer(transaction.getPayer());
        newPayer.setPoints(transaction.getPoints());
        entityManager.merge(newPayer);
    }

    @Override
    public List<Transaction> fetchAllTransactions() {
        Query theQuery = entityManager.createQuery("select payer,points,timestamp from Transaction order by timestamp");
        List<Transaction> transactions =theQuery.getResultList();
        return transactions;
    }

    @Override
    public HashMap<String, Integer> spendPoints(int points) {
        Query theQuery = entityManager.createQuery("select distinct payer,points,timestamp from Transaction order by timestamp");
        List<Transaction> transactions = theQuery.getResultList();
        int spendPoints = 0;
        HashMap<String, Integer> remainingPoints = new HashMap<String, Integer>();
        while(points !=0)
        {
            for(Transaction transaction: transactions) {
                spendPoints = transaction.getPoints();
                if(points > spendPoints){
                    points -= spendPoints;
                    if(remainingPoints.containsKey(transaction.getPayer())){
                        int tempPoints = remainingPoints.get(transaction.getPayer());
                        remainingPoints.put(transaction.getPayer(), tempPoints+spendPoints);
                    }
                    else{
                        remainingPoints.put(transaction.getPayer(), spendPoints);
                    }
                    spendPoints = 0;
                    Payer updatePoints = new Payer();
                    updatePoints.setPoints(spendPoints);
                }
                else if(points < spendPoints){
                    spendPoints -= spendPoints - points;
                    remainingPoints.put(transaction.getPayer(), points);
                    points = 0;
                    //UPDATE SPENDPOINTS IN DATABASE
                    Payer updatePoints = new Payer();
                    updatePoints.setPoints(spendPoints);
                }
            }
        }
        return remainingPoints;
    }
}
