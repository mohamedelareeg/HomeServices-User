package com.rovaindu.homeservice.model;

import com.rovaindu.homeservice.retrofit.models.Service;

import java.util.List;

public class PojoCart {
    private int id;
    private int agentid;
    private String location;
    private String locationnotes;
    private List<Service> services;
    private String day;
    private String hour;

    public PojoCart(int id, int agentid, String location, String locationnotes, List<Service> services, String day, String hour) {
        this.id = id;
        this.agentid = agentid;
        this.location = location;
        this.locationnotes = locationnotes;
        this.services = services;
        this.day = day;
        this.hour = hour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgentid() {
        return agentid;
    }

    public void setAgentid(int agentid) {
        this.agentid = agentid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationnotes() {
        return locationnotes;
    }

    public void setLocationnotes(String locationnotes) {
        this.locationnotes = locationnotes;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
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
