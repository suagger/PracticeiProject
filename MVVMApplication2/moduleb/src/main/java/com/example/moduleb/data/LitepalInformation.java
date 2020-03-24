package com.example.moduleb.data;

import org.litepal.crud.LitePalSupport;

public class LitepalInformation extends LitePalSupport {
    int year;
    String time;
    int month;
    String information;
    int day;
    int hour;
    int minute;

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;

    }


    public int getHour() {
        return hour;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;

    }

    public void setHour(int hour) {
        this.hour = hour;

    }

    public LitepalInformation(int year, int month, int day,int hour,int minute,String information,String time){
        this.information = information;
        this.day = day;
        this.month = month;
        this.minute = minute;
        this.hour = hour;
        this.year = year;
        this.time = time;
    }

    public int getDay() {
        return day;
    }


    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;

    }

    public void setDay(int day) {
        this.day = day;

    }


    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;

    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;

    }

}
