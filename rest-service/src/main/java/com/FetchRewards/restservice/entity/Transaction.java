package com.FetchRewards.restservice.entity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="payer")
    private String payer;

    @Column(name="points")
    private int points;

    @Column(name="timestamp")
    private Date timestamp;

    //Generate a constructor
    public Transaction(String payer, int points, Date timestamp) {
        this.payer = payer;
        this.points = points;
        this.timestamp = timestamp;
    }

    public Transaction(String payer, int points) {
        this.payer = payer;
        this.points = points;
    }

    public Transaction() {

    }

    public Transaction(Transaction transaction){
        this(transaction.getPayer(),transaction.getPoints(),transaction.getTimestamp());
    }

    public String getPayer() {
        return payer;
    }

    public int getPoints() {
        return points;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    //Define toString

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", payer='" + payer + '\'' +
                ", points=" + points +
                ", timestamp=" + timestamp +
                '}';
    }
}
