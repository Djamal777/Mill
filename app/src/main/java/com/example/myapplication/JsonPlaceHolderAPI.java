package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderAPI {
    public static String HOST = "https://engine.lifeis.porn/api/";

    @GET("millionaire.php")
    Call<QuestionResponse> getQuestions(
            @Query("qType") Integer qType,
            @Query("count") Integer count
    );
}
