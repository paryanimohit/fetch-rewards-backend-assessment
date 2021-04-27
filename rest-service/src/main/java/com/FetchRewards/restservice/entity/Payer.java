package com.FetchRewards.restservice.entity;

import javax.persistence.*;

@Entity
@Table(name="payer")
public class Payer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="payer")
    private String payer;

    @Column(name="points")
    private int points;

    public Payer() {

    }

    public String getPayer() {
        return payer;
    }

    public int getPoints() {
        return points;
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

    public Payer(String payer, int points) {
        this.id = id;
        this.payer = payer;
        this.points = points;
    }

    @Override
    public String toString() {
        return "Payer{" +
                "id=" + id +
                ", payer='" + payer + '\'' +
                ", points=" + points +
                '}';
    }
}
