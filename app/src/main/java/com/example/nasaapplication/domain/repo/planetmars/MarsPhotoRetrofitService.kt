package com.example.nasaapplication.domain.repo.planetmars

import com.example.nasaapplication.domain.model.MarsResponseEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val MARS_ROVER_PHOTOS_URL = "mars-photos/api/v1/rovers/curiosity/photos"

interface MarsPhotoRetrofitService {
    @GET(MARS_ROVER_PHOTOS_URL)
    fun getMarsPhotos(
        @Query("earth_date") date: String,
        @Query("api_key") key: String
    ): Call<MarsResponseEntity>
}