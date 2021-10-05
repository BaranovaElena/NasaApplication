package com.example.nasaapplication.domain.repo.planetmars

import com.example.nasaapplication.domain.model.MarsResponseEntity
import retrofit2.Callback

class MarsPhotoRetrofitRepoImpl : MarsPhotoRepo {
    override fun getMarsPhoto(
        callback: Callback<MarsResponseEntity>,
        service: MarsPhotoRetrofitService,
        date: String,
        key: String
    ) {
        service.getMarsPhotos(date, key).enqueue(callback)
    }
}
