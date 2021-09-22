package com.example.nasaapplication.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EarthPhotoEntity(
    val caption: String = "",
    val image: String = "",
    val date: String = ""
) : Parcelable