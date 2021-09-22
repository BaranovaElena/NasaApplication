package com.example.nasaapplication

import android.app.Application
import com.example.nasaapplication.domain.repo.planetearth.EarthPhotoRetrofitService
import com.example.nasaapplication.domain.repo.planetearth.EarthPhotoRepo
import com.example.nasaapplication.domain.repo.planetearth.EarthPhotoRepoImplRetrofit
import com.example.nasaapplication.domain.repo.planetmars.MarsPhotoRepo
import com.example.nasaapplication.domain.repo.planetmars.MarsPhotoRepoImplRetrofit
import com.example.nasaapplication.domain.repo.planetmars.MarsPhotoRetrofitService
import com.example.nasaapplication.domain.repo.pod.PodRepo
import com.example.nasaapplication.domain.repo.pod.PodRepoImplRetrofit
import com.example.nasaapplication.domain.repo.pod.PodRetrofitService
import com.example.nasaapplication.domain.repo.spaceweather.SpaceWeatherRepo
import com.example.nasaapplication.domain.repo.spaceweather.SpaceWeatherRepoImplRetrofit
import com.example.nasaapplication.domain.repo.spaceweather.SpaceWeatherRetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NasaApplication : Application() {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiKey = BuildConfig.POD_KEY

    val podService: PodRetrofitService by lazy {
        retrofit.create(PodRetrofitService::class.java)
    }
    val podRepo: PodRepo = PodRepoImplRetrofit()

    val spaceWeatherService: SpaceWeatherRetrofitService by lazy {
        retrofit.create(SpaceWeatherRetrofitService::class.java)
    }
    val spaceWeatherRepo: SpaceWeatherRepo = SpaceWeatherRepoImplRetrofit()

    val earthPhotoService: EarthPhotoRetrofitService by lazy {
        retrofit.create(EarthPhotoRetrofitService::class.java)
    }
    val earthPhotoRepo: EarthPhotoRepo = EarthPhotoRepoImplRetrofit()

    val marsPhotoService: MarsPhotoRetrofitService by lazy {
        retrofit.create(MarsPhotoRetrofitService::class.java)
    }
    val marsPhotoRepo: MarsPhotoRepo = MarsPhotoRepoImplRetrofit()
}