package com.FetchRewards.restservice.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {

    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    @Column(name="payer")
    private String payer;

    @Column(name="points")
    private int points;

    @Column(name="timestamp")
    private Date timeStamp;

    //Generate a constructor
    public Transaction(String payer, int points, Date timeStamp) {
        this.payer = payer;
        this.points = points;
        this.timeStamp = timeStamp;
    }

    //Define Getter Setters

    public int getId() {
        return id;
    }

    public String getPayer() {
        return payer;
    }

    public int getPoints() {
        return points;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    //Define toString

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", payer='" + payer + '\'' +
                ", points=" + points +
                ", timeStamp=" + timeStamp +
                '}';
    }
}