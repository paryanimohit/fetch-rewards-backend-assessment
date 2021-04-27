package com.FetchRewards.restservice.utility;

import com.FetchRewards.restservice.entity.Payer;
import com.FetchRewards.restservice.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class SpendPointsHelper {
    public List<Payer> SpendPointsByTimeStamp(List<Transaction> transactionData, Transaction transaction){

        int points = transaction.getPoints();
        int spendPoints;
        int tempPoints;
        int tempIndex;
        List<Transaction> pointsSpent = new ArrayList<>();
        List<String> tempPayers = new ArrayList<>();

            while (points != 0) {
                for (Transaction t : transactionData) {
                    spendPoints = t.getPoints();
                    if (points > spendPoints) {
                        points -= spendPoints;
                        if (tempPayers.contains(t.getPayer())) {
                            tempIndex = tempPayers.indexOf(t.getPayer());
                            tempPoints = pointsSpent.get(tempIndex).getPoints();
                            t.setPoints(Math.abs(spendPoints) + tempPoints);
                            pointsSpent.remove(tempIndex);
                        } else {
                            t.setPoints(-spendPoints);
                        }
                        pointsSpent.add(t);
                        tempPayers.add(t.getPayer());
                    } else if (points < spendPoints) {
                        t.setPoints(-points);
                        pointsSpent.add(t);
                        points = 0;
                        break;
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
