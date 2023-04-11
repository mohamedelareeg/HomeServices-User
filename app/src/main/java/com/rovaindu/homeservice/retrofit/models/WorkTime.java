package com.rovaindu.homeservice.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rovaindu.homeservice.retrofit.Unavailable;

import java.io.Serializable;
import java.util.List;

public class WorkTime implements Serializable {

    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("unavailable")
    @Expose
    private List<Unavailable> unavailable = null;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<Unavailable> getUnavailable() {
        return unavailable;
    }

    public void setUnavailable(List<Unavailable> unavailable) {
        this.unavailable = unavailable;
    }


}
