package com.example.nasaapplication.ui.pod

import com.example.nasaapplication.R

sealed class LoadPodState {
    class Success(val title: String, val picture: String, val description: String) : LoadPodState()
    object Loading : LoadPodState()
    data class Error(val error: Int) : LoadPodState()
}

class LoadPodError {
    companion object {
        const val LOAD_ERROR = R.string.load_error
        const val SERVER_ERROR = R.string.server_error
        const val NOT_IMAGE_ERROR = R.string.not_image_error
    }
}
