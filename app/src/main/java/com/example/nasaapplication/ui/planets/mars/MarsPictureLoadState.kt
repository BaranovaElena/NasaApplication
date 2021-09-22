package com.example.nasaapplication.ui.planets.mars

sealed class MarsPictureLoadState {
    class Success(val sol: String, val date: String, val imagePath: String) : MarsPictureLoadState()
    object Loading : MarsPictureLoadState()
    data class Error(val error: MarsPictureLoadError) : MarsPictureLoadState()
}

enum class MarsPictureLoadError {
    LOAD_ERROR, SERVER_ERROR
}
