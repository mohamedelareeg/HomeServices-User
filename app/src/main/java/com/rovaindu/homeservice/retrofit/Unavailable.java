package com.rovaindu.homeservice.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Unavailable implements Serializable {

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("hour")
    @Expose
    private String hour;

    public Unavailable(String day, String hour) {
        this.day = day;
        this.hour = hour;
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


}

