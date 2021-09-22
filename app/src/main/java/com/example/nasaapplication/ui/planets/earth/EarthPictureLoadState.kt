package com.example.nasaapplication.ui.planets.earth

sealed class EarthPictureLoadState {
    class Success(val caption: String, val date: String, val imagePath: String) : EarthPictureLoadState()
    object Loading : EarthPictureLoadState()
    data class Error(val error: EarthPictureLoadError) : EarthPictureLoadState()
}

enum class EarthPictureLoadError {
    LOAD_ERROR, SERVER_ERROR
}
