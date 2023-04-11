package com.rovaindu.homeservice.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ServiesAgent implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("is_completed")
    @Expose
    private Boolean isCompleted;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("work_time")
    @Expose
    private WorkTime workTime;
    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("rates_count")
    @Expose
    private Integer ratesCount;
    @SerializedName("rates")
    @Expose
    private List<Rate> rates = null;
    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("experience_years")
    @Expose
    private Integer experienceYears;
    @SerializedName("hourly_wage")
    @Expose
    private String hourlyWage;
    @SerializedName("expire_date")
    @Expose
    private String expireDate;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("fcm_token")
    @Expose
    private String fcmToken;
    @SerializedName("plan")
    @Expose
    private Plan plan;
    @SerializedName("country")
    @Expose
    private Country country;
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("job")
    @Expose
    private Job job;
    @SerializedName("services")
    @Expose
    private List<Service> services = null;



    public ServiesAgent(Integer id, Boolean isCompleted, String name, String email, String location, String phone, WorkTime workTime , Integer rate, Integer ratesCount, List<Rate> rates, String experience, Integer experienceYears, String hourlyWage, String expireDate, String image, String apiToken, String fcmToken, Plan plan, Country country, City city, Job job, List<Service> services) {
        this.id = id;
        this.isCompleted = isCompleted;
        this.name = name;
        this.email = email;
        this.location = location;
        this.phone = phone;
        this.workTime = workTime;
        this.rate = rate;
        this.ratesCount = ratesCount;
        this.rates = rates;
        this.experience = experience;
        this.experienceYears = experienceYears;
        this.hourlyWage = hourlyWage;
        this.expireDate = expireDate;
        this.image = image;
        this.apiToken = apiToken;
        this.fcmToken = fcmToken;
        this.plan = plan;
        this.country = country;
        this.city = city;
        this.job = job;
        this.services = services;
    }

    public ServiesAgent(Integer id, String name, String email, String location, String phone, WorkTime workTime , Integer rate, Integer ratesCount, List<Rate> rates, String experience, Integer experienceYears, String hourlyWage, String expireDate, String image, String apiToken, String fcmToken, Plan plan, Country country, City city, Job job, List<Service> services) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.location = location;
        this.phone = phone;
        this.workTime = workTime;
        this.rate = rate;
        this.ratesCount = ratesCount;
        this.rates = rates;
        this.experience = experience;
        this.experienceYears = experienceYears;
        this.hourlyWage = hourlyWage;
        this.expireDate = expireDate;
        this.image = image;
        this.apiToken = apiToken;
        this.fcmToken = fcmToken;
        this.plan = plan;
        this.country = country;
        this.city = city;
        this.job = job;
        this.services = services;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public WorkTime getWorkTime() {
        return workTime;
    }

    public void setWorkTime(WorkTime workTime) {
        this.workTime = workTime;
    }
    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getRatesCount() {
        return ratesCount;
    }

    public void setRatesCount(Integer ratesCount) {
        this.ratesCount = ratesCount;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(String hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
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

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

}