package com.example.nasaapplication.domain.repo.pod

import com.example.nasaapplication.domain.model.PodEntity
import retrofit2.Callback

class PodRetrofitRepoImpl : PodRepo {
    override fun getPictureOfTheDay(
        callback: Callback<PodEntity>,
        service: PodRetrofitService,
        date: String,
        key: String) {
        service.getPictureOfTheDay(date, key).enqueue(callback)
    }
}