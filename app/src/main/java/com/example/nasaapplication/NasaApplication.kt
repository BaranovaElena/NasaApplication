package com.example.nasaapplication

import android.app.Application
import com.example.nasaapplication.domain.repo.pod.PodRepo
import com.example.nasaapplication.domain.repo.pod.PodRepoImplRetrofit
import com.example.nasaapplication.domain.repo.pod.PodRetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NasaApplication : Application() {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service: PodRetrofitService by lazy {
        retrofit.create(PodRetrofitService::class.java)
    }
    val apiKey = BuildConfig.POD_KEY
    val podRepo: PodRepo = PodRepoImplRetrofit()
}