package com.example.nasaapplication.ui.pod

import android.accounts.AuthenticatorDescription

sealed class LoadPodState {
    class Success(val title: String, val picture: String, val description: String) : LoadPodState()
    object Loading : LoadPodState()
    data class Error(val error: Throwable) : LoadPodState()
}
