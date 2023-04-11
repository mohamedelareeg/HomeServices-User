package com.rovaindu.homeservice.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rovaindu.homeservice.model.UserAddress;
import com.rovaindu.homeservice.retrofit.models.City;
import com.rovaindu.homeservice.retrofit.models.Country;
import com.rovaindu.homeservice.retrofit.models.Service;
import com.rovaindu.homeservice.retrofit.models.ServiesUser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ServiesSharedPrefManager {

    private static ServiesSharedPrefManager mInstance;
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
    private static final String KEY_USER_GCM = "keyusergcm";
    private static final String KEY_USER_API = "keyuserapi";

    private static final String KEY_USER_CART = "keyusercart";
    private static final String KEY_USER_SERVICES = "keyuserservices";

    private static final String KEY_CURRENT_ADDRESS = "keycurrentaddress";
    private static final String KEY_LAST_DELIVERED_MESSAGEID = "LAST_DELIVERED_MESSAGEID";

    public ServiesSharedPrefManager(Context context) {
        mCtx = context;
        isInitialized = true;
        pref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized ServiesSharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ServiesSharedPrefManager(context);
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

    public boolean userLogin(ServiesUser user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonCity = gson.toJson(user.getCity());
        String jsonCountry = gson.toJson(user.getCountry());
        editor.putInt(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_THUMB, user.getImage());
        editor.putString(KEY_USER_COUNTRY, jsonCountry);
        editor.putString(KEY_USER_CITY, jsonCity);
        editor.putString(KEY_USER_PHONE, user.getPhone());
        editor.putString(KEY_USER_ADDRESS, user.getLocation());
        editor.putString(KEY_USER_API, user.getApiToken());
        editor.putString(KEY_USER_GCM, user.getFcmToken());
        editor.apply();
        return true;
    }

    public boolean setOLocation(UserAddress userAddress) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userAddress);
        editor.putString(KEY_CURRENT_ADDRESS, json);
        editor.apply();
        return true;
    }

    public boolean setServices(List<Service> service) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(service);
        editor.putString(KEY_USER_SERVICES, json);
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

    public ServiesUser getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonCountry = sharedPreferences.getString(KEY_USER_COUNTRY, null );
        String jsonCity = sharedPreferences.getString(KEY_USER_CITY, null );
        Country userCountry = gson.fromJson(jsonCountry, Country.class);
        City userCity = gson.fromJson(jsonCity, City.class);
        return new ServiesUser(
                sharedPreferences.getInt(KEY_USER_ID, 0),
                sharedPreferences.getString(KEY_USER_NAME, null),
                userCity,
                userCountry,
                sharedPreferences.getString(KEY_USER_EMAIL, null),
                sharedPreferences.getString(KEY_USER_PHONE, null),
                sharedPreferences.getString(KEY_USER_ADDRESS, null),
                sharedPreferences.getString(KEY_USER_THUMB, null),
                sharedPreferences.getString(KEY_USER_API, null),
                sharedPreferences.getString(KEY_USER_GCM, null)
        );
    }
    public boolean isCarted() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_SERVICES, null) != null)
            return true;
        return false;
    }
    public List<Service> getServices() {
        List<Service> serviceList = new ArrayList<>();
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Service>>(){}.getType();
        String serializedObject = sharedPreferences.getString(KEY_USER_SERVICES, null);
        if (serializedObject != null) {
            serviceList = gson.fromJson(serializedObject, type);
        }

        return serviceList;
    }
    public UserAddress getUserAddressT() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_CURRENT_ADDRESS, null );
        UserAddress userAddress = gson.fromJson(json, UserAddress.class);
        return userAddress;
    }

    public String getUserAddress() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ADDRESS, null);
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
