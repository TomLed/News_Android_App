package com.example.yourdailydigest;

import java.net.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCLient {
    private static final String BASE_URL = "https://newsapi.org/v2/";
    private static ApiCLient apiCLient;
    private static Retrofit retrofit;

    private ApiCLient(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized ApiCLient getInstance() {
        if (apiCLient == null) {
            apiCLient = new ApiCLient();
        }
        return apiCLient;
    }

    public ApiInterface getApi() {
        return retrofit.create(ApiInterface.class);
    }
}
