package com.example.nasaapplication.ui.weather.space

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nasaapplication.R

class SpaceWeatherFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_space_weather, container, false)
    }

    companion object {
        fun newInstance() = SpaceWeatherFragment()
    }
}