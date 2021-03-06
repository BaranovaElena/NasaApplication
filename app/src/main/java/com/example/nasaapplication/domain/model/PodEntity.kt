package com.example.nasaapplication.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PodEntity(
    val date: String = "",
    val explanation: String = "",
    val url: String = "",
    @SerializedName("media_type")
    val mediaType: String = "",
    val title: String = ""
) : Parcelable