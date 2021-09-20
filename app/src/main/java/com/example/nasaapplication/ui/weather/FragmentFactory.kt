package com.example.nasaapplication.ui.weather

import androidx.fragment.app.Fragment

data class FragmentFactory(
    val title: String,
    val iconId: Int,
    val factoryMethod: () -> Fragment
)
