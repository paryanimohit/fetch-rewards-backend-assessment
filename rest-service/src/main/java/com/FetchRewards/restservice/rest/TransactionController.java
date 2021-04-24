package com.FetchRewards.restservice.rest;

import com.FetchRewards.restservice.entity.Transaction;
import com.FetchRewards.restservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService theTransactionService) {
        transactionService = theTransactionService;
    }
        @GetMapping("/transactions")
        public List<Transaction> fetchAllTransactions(){
            return transactionService.fetchAllTransactions();
        }

        @PostMapping("/transactions")
        public Transaction addTransaction(@RequestBody Transaction transaction){

            System.out.println(transaction.getPayer());
            System.out.println(transaction.getPoints());
            System.out.println(transaction.getTimeStamp());
            transaction.setId(0);
            transactionService.addTransaction(transaction);
            return transaction;
    }
}
