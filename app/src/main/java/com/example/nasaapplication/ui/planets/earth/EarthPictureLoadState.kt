package com.example.nasaapplication.ui.planets.earth

import com.example.nasaapplication.ui.LoadError

sealed class EarthPictureLoadState {
    class Success(val caption: String, val date: String, val imagePath: String) : EarthPictureLoadState()
    object Loading : EarthPictureLoadState()
    data class Error(val error: LoadError) : EarthPictureLoadState()
}
