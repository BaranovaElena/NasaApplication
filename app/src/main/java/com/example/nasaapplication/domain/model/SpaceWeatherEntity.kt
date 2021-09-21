package com.example.nasaapplication.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpaceWeatherEntity(
    val messageType: String = "",
    val messageID: String = "",
    val messageURL: String = "",
    val messageIssueTime: String = "",
    val messageBody: String = ""
) : Parcelable