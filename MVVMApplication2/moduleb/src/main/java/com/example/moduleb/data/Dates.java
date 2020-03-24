package com.example.moduleb.data;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.example.moduleb.R;


public class Dates extends BaseObservable implements IBaseBindingAdapterItem,Comparable<Dates> {
    int year;
    String time;
    int month;
    String information;
    int day;
    int hour;
    int minute;
    @Bindable
    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
        notifyPropertyChanged(BR.minute);
    }

    @Bindable
    public int getHour() {
        return hour;
    }

    @Bindable
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
        notifyPropertyChanged(BR.time);
    }

    public void setHour(int hour) {
        this.hour = hour;
        notifyPropertyChanged(BR.hour);
    }

    public Dates(int year, int month, int day,int hour,int minute,String information,String time){
        this.information = information;
        this.day = day;
        this.month = month;
        this.minute = minute;
        this.hour = hour;
        this.year = year;
        this.time = time;
    }
    @Bindable
    public int getDay() {
        return day;
    }

    @Bindable
    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
        notifyPropertyChanged(BR.information);
    }

    public void setDay(int day) {
        this.day = day;
        notifyPropertyChanged(BR.day);
    }

    @Bindable
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
        notifyPropertyChanged(BR.month);
    }

    @Bindable
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        notifyPropertyChanged(BR.year);
    }

    @Override
    public int getIteViewType() {
        return R.layout.item_recycler;
    }

    @Override
    public int compareTo(Dates o) {
        if (year != o.year){
            return year - o.year;
        }else{
            if(month != o.month){
                return month - o.month;
            }else{
                if(day != o.day){
                    return day - o.day;
                }else{
                    if(hour != o.hour){
                        return hour - o.hour;
                    }else{
                        if(minute != o.minute){
                            return minute - o.minute;
                        }
                    }
                }
            }
        }
        return minute - o.minute;
    }
}