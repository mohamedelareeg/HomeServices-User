package com.rovaindu.homeservice.model;

import com.rovaindu.homeservice.retrofit.models.Service;

import java.io.Serializable;
import java.util.List;

public class CategoryOld implements Serializable {

    private String name;
    private int image;
    private int id;
    private int type;
    private List<SubCategories> subCategories;
    private List<Service> agentServies;

    public CategoryOld() {
    }

    public CategoryOld(String name, int image, int id, int type, List<SubCategories> subCategories, List<Service> agentServies) {
        this.name = name;
        this.image = image;
        this.id = id;
        this.type = type;
        this.subCategories = subCategories;
        this.agentServies = agentServies;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SubCategories> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategories> subCategories) {
        this.subCategories = subCategories;
    }

    public List<Service> getAgentServies() {
        return agentServies;
    }

    public void setAgentServies(List<Service> agentServies) {
        this.agentServies = agentServies;
    }
}