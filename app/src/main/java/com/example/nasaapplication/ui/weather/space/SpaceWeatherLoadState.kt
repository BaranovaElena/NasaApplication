package com.example.nasaapplication.ui.weather.space

sealed class SpaceWeatherLoadState {
    class Success(val messageType: String, val messageIssueTime: String, val messageBody: String) : SpaceWeatherLoadState()
    object Loading : SpaceWeatherLoadState()
    data class Error(val error: SpaceWeatherLoadError) : SpaceWeatherLoadState()
}

enum class SpaceWeatherLoadError {
    LOAD_ERROR, SERVER_ERROR
}
