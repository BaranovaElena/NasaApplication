package com.example.nasaapplication.ui.pod

import com.example.nasaapplication.ui.LoadPodError

sealed class LoadPodState {
    class Success(val title: String, val picture: String, val description: String) : LoadPodState()
    object Loading : LoadPodState()
    data class Error(val error: LoadPodError) : LoadPodState()
}
