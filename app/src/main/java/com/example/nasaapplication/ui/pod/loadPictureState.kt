package com.example.nasaapplication.ui.pod

sealed class LoadPodState {
    class Success(val title: String, val picture: String, val description: String) : LoadPodState()
    object Loading : LoadPodState()
    data class Error(val error: LoadPodError) : LoadPodState()
}

enum class LoadPodError {
    LOAD_ERROR, SERVER_ERROR, NOT_IMAGE_ERROR
}
