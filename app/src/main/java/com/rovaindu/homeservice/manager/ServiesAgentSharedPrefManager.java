package com.rovaindu.homeservice.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rovaindu.homeservice.model.UserAddress;
import com.rovaindu.homeservice.retrofit.models.City;
import com.rovaindu.homeservice.retrofit.models.Country;
import com.rovaindu.homeservice.retrofit.models.Job;
import com.rovaindu.homeservice.retrofit.models.Plan;
import com.rovaindu.homeservice.retrofit.models.Rate;
import com.rovaindu.homeservice.retrofit.models.Service;
import com.rovaindu.homeservice.retrofit.models.ServiesAgent;
import com.rovaindu.homeservice.retrofit.models.WorkTime;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ServiesAgentSharedPrefManager {

    private static ServiesAgentSharedPrefManager mInstance;
    private static Context mCtx;
    // Shared Preferences
    SharedPreferences pref;
    private static final String SHARED_PREF_NAME = "serviesuserapp";
    private static final String KEY_APPID = "APPID";
    public static boolean isInitialized = false;
    private static final String KEY_GUEST_VISIT = "guestvisit";

    private static final String KEY_USER_ID = "keyuserid";
    private static final String KEY_USER_NAME = "keyusername";
    private static final String KEY_USER_EMAIL = "keyuseremail";
    private static final String KEY_USER_THUMB = "keyuserthumb";
    private static final String KEY_USER_COUNTRY = "keyusercountry";
    private static final String KEY_USER_CITY = "keyusercity";
    private static final String KEY_USER_PHONE = "keyuserphone";
    private static final String KEY_USER_ADDRESS = "keyuseraddress";
    private static final String KEY_USER_RATE = "keyuserrate";
    private static final String KEY_USER_RATE_COUNT = "keyuserratecount";
    private static final String KEY_USER_RATES = "keyuserrates";
    private static final String KEY_USER_EXPIRENCE = "keyuserexperience";
    private static final String KEY_USER_EXPIRENCE_YEARS = "keyuserexperienceYears";
    private static final String KEY_USER_HOURLY_WAGES = "keyuserhourlyWage";
    private static final String KEY_USER_EXPIRE_DATE = "keyuserexpireDate";
    private static final String KEY_USER_PLAN = "keyuserplan";
    private static final String KEY_USER_WORK_TIME = "keyuserworktime";
    private static final String KEY_USER_JOB = "keyuserjob";
    private static final String KEY_USER_GCM = "keyusergcm";
    private static final String KEY_USER_API = "keyuserapi";
    private static final String KEY_USER_SERVIES= "keyuserservies";

    private static final String KEY_LAST_DELIVERED_MESSAGEID = "LAST_DELIVERED_MESSAGEID";

    public ServiesAgentSharedPrefManager(Context context) {
        mCtx = context;
        isInitialized = true;
        pref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized ServiesAgentSharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ServiesAgentSharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userToken(String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_GCM, token);
        editor.apply();
        return true;
    }
    public boolean userLocation(String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ADDRESS, token);
        editor.apply();
        return true;
    }

    public boolean userLogin(ServiesAgent user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonWorkTime = gson.toJson(user.getWorkTime());
        String jsonCity = gson.toJson(user.getCity());
        String jsonCountry = gson.toJson(user.getCountry());
        String jsonPLan = gson.toJson(user.getPlan());
        String jsonJob = gson.toJson(user.getJob());
        String jsonRates = gson.toJson(user.getRates());
        String jsonServies = gson.toJson(user.getServices());
        editor.putInt(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_THUMB, user.getImage());
        editor.putString(KEY_USER_WORK_TIME, jsonWorkTime);
        editor.putInt(KEY_USER_RATE, user.getRate());
        editor.putInt(KEY_USER_RATE_COUNT, user.getRatesCount());
        editor.putString(KEY_USER_RATES, jsonRates);
        editor.putString(KEY_USER_EXPIRENCE, user.getExperience());
        editor.putInt(KEY_USER_EXPIRENCE_YEARS, user.getExperienceYears());
        editor.putString(KEY_USER_EXPIRE_DATE, user.getExpireDate());
        editor.putString(KEY_USER_HOURLY_WAGES, user.getHourlyWage());
        editor.putString(KEY_USER_COUNTRY, jsonCountry);
        editor.putString(KEY_USER_CITY, jsonCity);
        editor.putString(KEY_USER_PLAN, jsonPLan);
        editor.putString(KEY_USER_JOB, jsonJob);
        editor.putString(KEY_USER_PHONE, user.getPhone());
        editor.putString(KEY_USER_ADDRESS, user.getLocation());
        editor.putString(KEY_USER_API, user.getApiToken());
        editor.putString(KEY_USER_GCM, user.getFcmToken());
        editor.putString(KEY_USER_SERVIES, jsonServies);
        editor.apply();
        return true;
    }

    public boolean setOLocation(UserAddress userAddress) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userAddress);
        editor.putString(KEY_USER_ADDRESS, json);
        editor.apply();
        return true;
    }
    public boolean GuestVisit( ) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_GUEST_VISIT  , 1);
        editor.apply();
        return true;
    }




    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_EMAIL, null) != null)
            return true;
        return false;
    }

    public ServiesAgent getUser() {

        List<Service> serviceList = new ArrayList<>();
        List<Rate> rateList = new ArrayList<>();
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonCountry = sharedPreferences.getString(KEY_USER_COUNTRY, null );
        String jsonCity = sharedPreferences.getString(KEY_USER_CITY, null );
        String jsonPlan = sharedPreferences.getString(KEY_USER_PLAN, null );
        String jsonJob = sharedPreferences.getString(KEY_USER_JOB, null );
        String jsonRates = sharedPreferences.getString(KEY_USER_RATES, null );
        String jsonWorkTime = sharedPreferences.getString(KEY_USER_WORK_TIME, null );
        String jsonServies = sharedPreferences.getString(KEY_USER_SERVIES, null );

        Country userCountry = gson.fromJson(jsonCountry, Country.class);
        City userCity = gson.fromJson(jsonCity, City.class);
        Plan userPlan = gson.fromJson(jsonPlan, Plan.class);
        Job userJob = gson.fromJson(jsonJob, Job.class);
        Rate userRates = gson.fromJson(jsonJob, Rate.class);
        WorkTime userWorkTime = gson.fromJson(jsonJob, WorkTime.class);
        //To Retrive A List of Object From SharedPerf
        Type type = new TypeToken<List<Service>>(){}.getType();
        String serializedObject = sharedPreferences.getString(KEY_USER_SERVIES, null);
        if (serializedObject != null) {
            serviceList = gson.fromJson(serializedObject, type);
        }

        Type typeRates = new TypeToken<List<Rate>>(){}.getType();
        String serializedObjectRate = sharedPreferences.getString(KEY_USER_RATES, null);
        if (serializedObjectRate != null) {
            rateList = gson.fromJson(serializedObjectRate, typeRates);
        }

        return new ServiesAgent(
                sharedPreferences.getInt(KEY_USER_ID, 0),
                sharedPreferences.getString(KEY_USER_NAME, null),
                sharedPreferences.getString(KEY_USER_EMAIL, null),
                sharedPreferences.getString(KEY_USER_ADDRESS, null),
                sharedPreferences.getString(KEY_USER_PHONE, null),
                userWorkTime,
                sharedPreferences.getInt(KEY_USER_RATE, 0),
                sharedPreferences.getInt(KEY_USER_RATE_COUNT, 0),
                rateList,
                sharedPreferences.getString(KEY_USER_EXPIRENCE, null),
                sharedPreferences.getInt(KEY_USER_EXPIRENCE_YEARS, 0),
                sharedPreferences.getString(KEY_USER_HOURLY_WAGES, null),
                sharedPreferences.getString(KEY_USER_EXPIRE_DATE, null),
                sharedPreferences.getString(KEY_USER_THUMB, null),
                sharedPreferences.getString(KEY_USER_API, null),
                sharedPreferences.getString(KEY_USER_GCM, null),
                userPlan,
                userCountry,
                userCity,
                userJob,
                serviceList
        );
    }

    public UserAddress getUserAddress() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_USER_ADDRESS, null );
        UserAddress userAddress = gson.fromJson(json, UserAddress.class);
        return userAddress;
    }


    public int getGuestVisit() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_GUEST_VISIT, 0);
    }

    //this method will fetch the device token from shared preferences
    public String getDeviceToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(KEY_USER_GCM, null);
    }
    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public static void clearPreferences() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("UID").apply();
        sharedPreferences.edit().remove("LAST_DELIVERED_MESSAGEID").apply();
    }

    public static void saveLastDeliveredMessageId(int messageId) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("LAST_DELIVERED_MESSAGEID", messageId);
        editor.apply();
    }

    public static int getLastDeliveredMessageId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("LAST_DELIVERED_MESSAGEID", 0);
    }

    public static void saveAppID(String appID) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("APPID", appID);
        editor.apply();
    }

    public static String getAppID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("APPID", (String)null);
    }

}
