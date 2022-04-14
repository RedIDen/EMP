package com.example.grow.Models;

public class Habit {
    String uid;
    String title;
    String flowerLink;
    //DateTime beginDate;
    boolean[] daysResults;

    public Habit(String uid, String title, String flowerLink,
                 //DateTime beginDate,
                 boolean[] daysResults) {
        this.uid = uid;
        this.title = title;
        this.flowerLink = flowerLink;
        //this.beginDate = beginDate;
        this.daysResults = daysResults;
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

//    public DateTime getBeginDate() {
//        return beginDate;
//    }
//
//    public void setBeginDate(DateTime beginDate) {
//        this.beginDate = beginDate;
//    }

    public boolean[] getDaysResults() {
        return daysResults;
    }

    public void setDaysResults(boolean[] daysResults) {
        this.daysResults = daysResults;
    }
}
