package com.example.nasaapplication.domain.repo.spaceweather

import com.example.nasaapplication.domain.model.SpaceWeatherEntity
import retrofit2.Callback

class SpaceWeatherRepoImplRetrofit: SpaceWeatherRepo {
    override fun getSpaceWeather(
        callback: Callback<List<SpaceWeatherEntity>>,
        service: SpaceWeatherRetrofitService,
        date: String,
        key: String
    ) {
        service.getSpaceWeather(date, date, key).enqueue(callback)
    }
}