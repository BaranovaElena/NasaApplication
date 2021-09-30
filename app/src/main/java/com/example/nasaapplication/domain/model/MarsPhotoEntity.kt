package com.example.nasaapplication.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarsResponseEntity(
    val photos: List<MarsPhotoEntity>?
) : Parcelable

@Parcelize
data class MarsPhotoEntity(
    val sol: String = "",
    @SerializedName("img_src")
    val image: String = "",
    @SerializedName("earth_date")
    val date: String = ""
) : Parcelable
