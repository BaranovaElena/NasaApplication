package com.example.nasaapplication.domain.repo.pod

import com.example.nasaapplication.BuildConfig
import com.example.nasaapplication.domain.model.pod.PodEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PodRetrofitService {
    @GET(BuildConfig.POD_URL)
    fun getPictureOfTheDay(
        @Query("api_key") key: String
    ): Call<PodEntity>
}