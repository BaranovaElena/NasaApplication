package com.example.nasaapplication.domain.repo.spaceweather

import com.example.nasaapplication.domain.model.SpaceWeatherEntity
import retrofit2.Callback

interface SpaceWeatherRepo {
    fun getSpaceWeather(
        callback: Callback<List<SpaceWeatherEntity>>,
        service: SpaceWeatherRetrofitService,
        date: String,
        key: String)
}