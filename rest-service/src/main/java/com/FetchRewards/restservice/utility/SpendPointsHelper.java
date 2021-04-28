package com.FetchRewards.restservice.utility;

import com.FetchRewards.restservice.entity.Payer;
import com.FetchRewards.restservice.entity.Transaction;
import com.FetchRewards.restservice.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpendPointsHelper {
    public List<Payer> SpendPointsByTimeStamp(List<Transaction> transactionData, Transaction transaction, TransactionRepository transactionRepository){

        int pointsToSpend = transaction.getPoints();
        int tempPoints, tempIndex;
        List<Transaction> pointsSpent = new ArrayList<>();
        List<String> tempPayers = new ArrayList<>();
        Date date = new Date();
            while (pointsToSpend > 0) {
                for (Transaction t : transactionData) {
                    int availablePointsToSpend = t.getPoints();
                    if (pointsToSpend >= availablePointsToSpend && availablePointsToSpend !=0) {
                        pointsToSpend -= availablePointsToSpend;
                        //ADD A NEW TRANSACTION WITH DEDUCTED POINTS AND TIMESTAMP
                        transactionRepository.save(new Transaction(t.getPayer(),-(availablePointsToSpend),date));
                        if (tempPayers.contains(t.getPayer())) {
                            tempIndex = tempPayers.indexOf(t.getPayer());
                            tempPoints = pointsSpent.get(tempIndex).getPoints();
                            t.setPoints(Math.abs(availablePointsToSpend) + tempPoints);
                            pointsSpent.remove(tempIndex);
                        } else {
                            t.setPoints(-availablePointsToSpend);
                        }
                        pointsSpent.add(t);
                        tempPayers.add(t.getPayer());
                    } else if (pointsToSpend < availablePointsToSpend) {
                        t.setPoints(-pointsToSpend);
                        transactionRepository.save(new Transaction(t.getPayer(),-(pointsToSpend),date));
                        pointsSpent.add(t);
                        pointsToSpend = 0;
                        break;
                    }
                    else if(availablePointsToSpend == 0){
                        continue;
                    }
                }
            }
        List<Payer> newPoints = new ArrayList<>();

        for(Transaction point: pointsSpent){
            String payerName = point.getPayer();
            int payerPoints = point.getPoints();
            Payer payer = new Payer();
            payer.setPayer(payerName);
            payer.setPoints(payerPoints);
            newPoints.add(payer);
        }

        return newPoints;
    }
}
