package com.FetchRewards.restservice.rest;

import com.FetchRewards.restservice.entity.Transaction;
import com.FetchRewards.restservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

      @GetMapping("/transactions")
        public ResponseEntity<List<Transaction>> fetchAllTransactions(){
            try {
                List<Transaction> transactions = new ArrayList<>();
                transactionRepository.findAll().forEach(transactions::add);
                if (transactions.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(transactions, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PostMapping("/transactions")
        public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction){
            try {
                Transaction newTransaction = transactionRepository.save(new Transaction(transaction.getPayer(), transaction.getPoints(), transaction.getTimestamp()));
                return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PostMapping("/transactions/points")
        public ResponseEntity<List<Transaction>> spendPoints(@RequestBody Transaction transaction){
            int points = transaction.getPoints();
            int spendPoints;
            int tempPoints;
            int tempIndex;
            List<Transaction> remainingPoints = new ArrayList<>();
            List<String> tempPayers = new ArrayList<>();
            List<Transaction> transactionData = transactionRepository.findAll(Sort.by("timestamp"));
            if (!(transactionData.isEmpty())){
                while(points!=0){
                    for(Transaction t: transactionData){
                        spendPoints = t.getPoints();
                        if(points > spendPoints){
                            points -= spendPoints;
                            if(tempPayers.contains(t.getPayer())){
                                tempIndex = tempPayers.indexOf(t.getPayer());
                                tempPoints = remainingPoints.get(tempIndex).getPoints();
                                t.setPoints(Math.abs(spendPoints)+tempPoints);
                                remainingPoints.remove(tempIndex);
                            }
                            else{
                                t.setPoints(-spendPoints);
                            }
                            remainingPoints.add(t);
                            tempPayers.add(t.getPayer());
                        }
                        else if(points < spendPoints){
                            t.setPoints(-points);
                            remainingPoints.add(t);
                            points = 0;
                            break;
                        }
                    }
                }
                return new ResponseEntity<>(remainingPoints, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
}
