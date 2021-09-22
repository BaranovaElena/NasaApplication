package com.example.nasaapplication.domain.repo.planetearth

import com.example.nasaapplication.domain.model.EarthPhotoEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val EPIC_URL = "EPIC/api/natural"

interface EarthPhotoRetrofitService {
    @GET(EPIC_URL)
    fun getEarthPhoto(
        @Query("api_key") key: String
    ): Call<List<EarthPhotoEntity>>
}