package com.rovaindu.homeservice.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {


    static Retrofit retrofit;
    public static final String APP_URL            = "https://service.horizon.net.sa";
    public static final String BASE_URL = APP_URL + "/home-services/public/";
    public static Retrofit getRetrofitInstance(){

        if(retrofit == null){

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient oauthsignature = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create()) //important
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(oauthsignature)
                    .build();
        }
        return retrofit;
    }
    public static Retrofit retrofitWrite(String lan)
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        return new Retrofit.Builder()
                .baseUrl(BASE_URL + lan + "/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    public static Retrofit retrofitAPIWrite(String lan , String APIKEY)
    {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor((okhttp3.Interceptor) chain -> {
            //rewrite the request to add bearer token
            okhttp3.Request newRequest=chain.request().newBuilder()
                    .addHeader("x-api-key",APIKEY)
                    .build();

            return chain.proceed(newRequest);
        });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        return new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(BASE_URL + lan+ "/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }



}
