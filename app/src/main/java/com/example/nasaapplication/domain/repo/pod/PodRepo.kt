package com.example.nasaapplication.domain.repo.pod

import com.example.nasaapplication.domain.model.PodEntity
import retrofit2.Callback

interface PodRepo {
    fun getPictureOfTheDay(
        callback: Callback<PodEntity>,
        service: PodRetrofitService,
        date: String,
        key: String)
}