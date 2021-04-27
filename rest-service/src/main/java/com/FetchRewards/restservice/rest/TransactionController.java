package com.FetchRewards.restservice.rest;

import com.FetchRewards.restservice.entity.Payer;
import com.FetchRewards.restservice.entity.Transaction;
import com.FetchRewards.restservice.repository.PayerRepository;
import com.FetchRewards.restservice.repository.TransactionRepository;
import com.FetchRewards.restservice.utility.SpendPointsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    PayerRepository payerRepository;

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
                Transaction newTransaction = transactionRepository
                        .save(new Transaction(transaction.getPayer(), transaction.getPoints(), transaction.getTimestamp()));
                try {
                    int tempPoints;
                    Payer payer = payerRepository.findByPayer(transaction.getPayer());
                    tempPoints = payer.getPoints()+transaction.getPoints();
                    payer.setPoints(tempPoints);
                    payerRepository.save(payer);
                }
                catch(Exception e){
                    payerRepository.save(new Payer(transaction.getPayer(), transaction.getPoints()));
                }
                return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PostMapping("/transactions/points")
        public ResponseEntity<List<Payer>> spendPoints(@RequestBody Transaction transaction){

            List<Transaction> transactionData = transactionRepository.findAll(Sort.by("timestamp"));
            List<Transaction> newTransactionData = new ArrayList<>();

            //Creating deep copy to prevent object call by reference (Copy Constructor)
            for(Transaction t: transactionData){
                Transaction newT = new Transaction(t);
                newTransactionData.add(newT);
            }

            Transaction newTransaction = new Transaction(transaction);

            if (!(transactionData.isEmpty())) {
                SpendPointsHelper h1 = new SpendPointsHelper();
                List<Payer> points = h1.SpendPointsByTimeStamp(newTransactionData,newTransaction);

                //Saving Remaining points to Payer repository
                int tempPoints;
                for(Payer point: points){
                    tempPoints = point.getPoints();
                    Payer payer = payerRepository.findByPayer(point.getPayer());
                    payer.setPoints(payer.getPoints() + tempPoints);
                    payerRepository.save(payer);
                }

                return new ResponseEntity<>(points, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        @GetMapping("/transactions/points")
        public ResponseEntity<HashMap<String, Integer>> fetchRemainingPoints(){
            try {
                List<Payer> remainingPoints = new ArrayList<>();
                HashMap<String, Integer> fetchedPoints = new HashMap<>();
                payerRepository.findAll().forEach(remainingPoints::add);
                if (remainingPoints.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                else{
                    for(Payer remainingPoint:remainingPoints){
                        fetchedPoints.put(remainingPoint.getPayer(),remainingPoint.getPoints());
                    }
                }
                return new ResponseEntity<>(fetchedPoints, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
}
