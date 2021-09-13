package com.example.nasaapplication.domain.model.pod

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

const val MEDIA_TYPE_IMAGE = "image"

@Parcelize
data class PodEntity(
    val date: String = "",
    val explanation: String = "",
    val url: String = "",
    val media_type: String = "",
    val title: String = ""
) : Parcelable