package com.rovaindu.homeservice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SelectedDate implements Serializable {

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("hour")
    @Expose
    private String hour;

    private String dayt;

    public SelectedDate(String day, String hour, String dayt) {
        this.day = day;
        this.hour = hour;
        this.dayt = dayt;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDayt() {
        return dayt;
    }

    public void setDayt(String dayt) {
        this.dayt = dayt;
    }
}
