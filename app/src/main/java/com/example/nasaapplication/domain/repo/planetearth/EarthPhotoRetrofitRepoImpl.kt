package com.example.nasaapplication.domain.repo.planetearth

import com.example.nasaapplication.domain.model.EarthPhotoEntity
import retrofit2.Callback

class EarthPhotoRetrofitRepoImpl : EarthPhotoRepo {
    override fun getEarthPhoto(
        callback: Callback<List<EarthPhotoEntity>>,
        service: EarthPhotoRetrofitService,
        key: String
    ) {
        service.getEarthPhoto(key).enqueue(callback)
    }
}