package com.example.nasaapplication.ui.weather.mars

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nasaapplication.R
import com.example.nasaapplication.databinding.FragmentMarsWeatherBinding

class MarsWeatherFragment : Fragment(R.layout.fragment_mars_weather) {
    private val binding: FragmentMarsWeatherBinding by viewBinding(
        FragmentMarsWeatherBinding::bind
    )

    companion object {
        fun newInstance() = MarsWeatherFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ObjectAnimator.ofFloat(
            binding.marsWeatherTitleTextView,
            View.TRANSLATION_Y,
            0f, 500f
        ).setDuration(2000).start()
    }
}