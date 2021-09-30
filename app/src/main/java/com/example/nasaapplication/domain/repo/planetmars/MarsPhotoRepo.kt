package com.example.nasaapplication.domain.repo.planetmars

import com.example.nasaapplication.domain.model.MarsResponseEntity
import retrofit2.Callback

interface MarsPhotoRepo {
    fun getMarsPhoto(
        callback: Callback<MarsResponseEntity>,
        service: MarsPhotoRetrofitService,
        date: String,
        key: String
    )
}