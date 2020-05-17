package com.example.myapplication;

import java.util.List;

import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Database {

    @GET("/api/millionaire.php")
    Call<QuestionDataList> data(@Query("qType") Integer qType, @Query("count") Integer count);

}