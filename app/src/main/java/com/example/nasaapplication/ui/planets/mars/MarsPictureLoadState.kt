package com.example.nasaapplication.ui.planets.mars

import com.example.nasaapplication.ui.LoadError

sealed class MarsPictureLoadState {
    class Success(val sol: String, val date: String, val imagePath: String) : MarsPictureLoadState()
    object Loading : MarsPictureLoadState()
    data class Error(val error: LoadError) : MarsPictureLoadState()
}
