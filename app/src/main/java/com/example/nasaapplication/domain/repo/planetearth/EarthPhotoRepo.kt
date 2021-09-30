package com.example.nasaapplication.domain.repo.planetearth

import com.example.nasaapplication.domain.model.EarthPhotoEntity
import retrofit2.Callback

interface EarthPhotoRepo {
    fun getEarthPhoto(
        callback: Callback<List<EarthPhotoEntity>>,
        service: EarthPhotoRetrofitService,
        key: String
    )
}