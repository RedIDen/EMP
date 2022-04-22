package com.example.grow.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Habit {
    private String uid;
    private String title;
    private String flowerLink;
    private Date beginDate;
    private long deltaDays;
    private boolean[] daysResults;

    public Habit(String uid, String title, String flowerLink,
                 String beginDate,
                 String daysResults) {
        this.uid = uid;
        this.title = title;
        this.flowerLink = flowerLink;
        try {
            this.beginDate = new SimpleDateFormat("MM.dd.yyyy").parse(beginDate);
            Date today = new Date();
            long diffInMilliseconds = today.getTime() - this.beginDate.getTime();
            this.deltaDays = TimeUnit.DAYS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        this.daysResults = new boolean[daysResults.length()];
        for (int i = 0; i < daysResults.length(); i++){
            this.daysResults[i] = daysResults.charAt(i) == '+';
        }
    }

    public long getDeltaDays() {
        return this.deltaDays;
    }

    public String getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFlowerLink() {
        return flowerLink;
    }

    public void setFlowerLink(String flowerLink) {
        this.flowerLink = flowerLink;
    }

    public boolean[] getDaysResults() {
        return daysResults;
    }

    public void setDaysResults(boolean[] daysResults) {
        this.daysResults = daysResults;
    }
}
