package com.example.nasaapplication.domain.repo.planetmars

import com.example.nasaapplication.domain.model.MarsResponseEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarsPhotoRetrofitService {
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsPhotos(
        @Query("earth_date") date: String,
        @Query("api_key") key: String
    ): Call<MarsResponseEntity>
}