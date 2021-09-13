package com.example.nasaapplication.domain.repo.pod

import com.example.nasaapplication.BuildConfig
import com.example.nasaapplication.NasaApplication
import com.example.nasaapplication.domain.model.pod.PodEntity
import retrofit2.Callback

class PodRepoImplRetrofit : PodRepo {
    private val service: PodRetrofitService by lazy {
        NasaApplication.podRetrofit.create(PodRetrofitService::class.java)
    }

    override fun getPictureOfTheDay(callback: Callback<PodEntity>) {
        service.getPictureOfTheDay(BuildConfig.POD_KEY).enqueue(callback)
    }
}