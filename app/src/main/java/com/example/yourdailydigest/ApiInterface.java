package com.example.yourdailydigest;

import com.example.yourdailydigest.Model.Headlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("top-headlines")
    Call<Headlines> getHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    @GET("top-headlines")
    Call<Headlines> getSource(
            @Query("sources") String sources,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<Headlines> getSpecific(
            @Query("q") String quer,
            @Query("apiKey") String apiKey
    );

}