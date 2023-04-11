package com.rovaindu.homeservice.model;

import java.io.Serializable;

public class Offer implements Serializable {

    private String name;
    private int image;
    private int id;

    public Offer() {
    }

    public Offer(String name, int image, int id) {
        this.name = name;
        this.image = image;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}