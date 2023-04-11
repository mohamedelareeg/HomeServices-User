package com.rovaindu.homeservice.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Plan implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("month_count")
    @Expose
    private Integer monthCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(Integer monthCount) {
        this.monthCount = monthCount;
    }

}
