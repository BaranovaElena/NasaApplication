package com.example.nasaapplication.ui.weather.space

import com.example.nasaapplication.ui.LoadError

sealed class SpaceWeatherLoadState {
    class Success(val messageType: String, val messageIssueTime: String, val messageBody: String) : SpaceWeatherLoadState()
    object Loading : SpaceWeatherLoadState()
    data class Error(val error: LoadError) : SpaceWeatherLoadState()
}