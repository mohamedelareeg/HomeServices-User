package com.rovaindu.homeservice.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rovaindu.homeservice.retrofit.response.UserProfileResponse;

import java.io.Serializable;

public class ServiesUser implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("country")
    @Expose
    private Country country;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("fcm_token")
    @Expose
    private String fcmToken;

    public ServiesUser(Integer id, String name, City city, Country country, String email, String phone, String location, String image, String apiToken, String fcmToken) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.country = country;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.image = image;
        this.apiToken = apiToken;
        this.fcmToken = fcmToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
