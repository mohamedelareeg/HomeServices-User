package com.rovaindu.homeservice.model;

import java.io.Serializable;

public class Coupon implements Serializable {


    private int id;
    private String code;
    private int categoryID;
    private double discount;
    private int used_limit;
    public Coupon()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getUsed_limit() {
        return used_limit;
    }

    public void setUsed_limit(int used_limit) {
        this.used_limit = used_limit;
    }
}