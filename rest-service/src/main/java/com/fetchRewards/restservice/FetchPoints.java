package com.fetchRewards.restservice;

import java.util.Date;

public class FetchPoints {

    private final String payer;
    private final int points;
    private final Date timeStamp;

    public FetchPoints(String payer, int points, Date timeStamp) {
        this.payer = payer;
        this.points = points;
        this.timeStamp = timeStamp;
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
}
