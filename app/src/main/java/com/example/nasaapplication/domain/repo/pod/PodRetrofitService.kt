package com.example.nasaapplication.domain.repo.pod

import com.example.nasaapplication.domain.model.pod.PodEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val POD_URL = "planetary/apod/"

interface PodRetrofitService {
    @GET(POD_URL)
    fun getPictureOfTheDay(
        @Query("date") date: String,
        @Query("api_key") key: String
    ): Call<PodEntity>
}