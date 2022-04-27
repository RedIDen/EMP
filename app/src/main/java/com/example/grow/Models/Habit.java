package com.example.grow.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Habit {
    private String uid;
    private String title;
    private int flowerId;
    private Date beginDate;
    private int deltaDays;
    private String daysResults;
    private boolean checkedToday;

    public Habit(String uid, String title, int flowerId,
                 String beginDate,
                 String daysResults) {
        this.uid = uid;
        this.title = title;
        this.flowerId = flowerId;
        try {
            this.beginDate = new SimpleDateFormat("MM.dd.yyyy").parse(beginDate);
            Date today = new Date();
            long diffInMilliseconds = today.getTime() - this.beginDate.getTime();
            this.deltaDays = (int)TimeUnit.DAYS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);
            this.deltaDays = this.deltaDays > 27 ? 27 : this.deltaDays;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        this.daysResults = daysResults;
        this.checkedToday = daysResults.charAt(this.deltaDays) == '+';
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

    public int getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(int flowerId) {
        this.flowerId = flowerId;
    }

    public String getDaysResults() {
        return daysResults;
    }

    public void setDaysResults(String daysResults) {
        this.daysResults = daysResults;
    }

    public boolean isCheckedToday() {
        return checkedToday;
    }

    public void setCheckedToday(boolean checkedToday) {
        this.checkedToday = checkedToday;
    }
}
