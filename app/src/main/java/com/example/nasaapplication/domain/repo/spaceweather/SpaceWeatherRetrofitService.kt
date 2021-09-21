package com.example.nasaapplication.domain.repo.spaceweather

import com.example.nasaapplication.domain.model.SpaceWeatherEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val SPACE_WEATHER_URL = "DONKI/notifications"

interface SpaceWeatherRetrofitService {
    @GET(SPACE_WEATHER_URL)
    fun getSpaceWeather(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("api_key") key: String
    ): Call<List<SpaceWeatherEntity>>
}