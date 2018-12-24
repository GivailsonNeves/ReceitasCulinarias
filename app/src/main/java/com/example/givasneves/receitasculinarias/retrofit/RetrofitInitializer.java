package com.example.givasneves.receitasculinarias.retrofit;

import com.example.givasneves.receitasculinarias.model.Recipe;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInitializer {

    private String BASE_API_PATH = "https://d17h27t6h515a5.cloudfront.net/";

    private OkHttpClient okHttpClient = new OkHttpClient()
        .newBuilder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build();


    private Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_API_PATH)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    public Services services = retrofit.create(Services.class);

}
