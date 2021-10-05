package com.example.nasaapplication.domain.repo.pod

import com.example.nasaapplication.domain.model.PodEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PodRetrofitService {
    @GET("planetary/apod/")
    fun getPictureOfTheDay(
        @Query("date") date: String,
        @Query("api_key") key: String
    ): Call<PodEntity>
}