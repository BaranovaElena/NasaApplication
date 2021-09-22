package com.example.nasaapplication.ui.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nasaapplication.R
import com.example.nasaapplication.databinding.FragmentWeatherBinding
import com.example.nasaapplication.ui.FragmentFactory
import com.example.nasaapplication.ui.NasaViewPagerAdapter
import com.example.nasaapplication.ui.weather.mars.MarsWeatherFragment
import com.example.nasaapplication.ui.weather.space.SpaceWeatherFragment
import com.google.android.material.tabs.TabLayoutMediator

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private val binding: FragmentWeatherBinding by viewBinding(FragmentWeatherBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragments = listOf(
            FragmentFactory(getString(R.string.weather_tab_space_title), R.drawable.ic_space) {
                SpaceWeatherFragment.newInstance()
            },
            FragmentFactory(getString(R.string.weather_tab_mars_title), R.drawable.ic_mars) {
                MarsWeatherFragment.newInstance()
            }
        )

        with(binding.weatherViewPager) {
            adapter = NasaViewPagerAdapter(requireActivity(), fragments)
        }

        TabLayoutMediator(binding.weatherTabLayout, binding.weatherViewPager) {tab, pos ->
            fragments[pos].let {
                tab.text = it.title
                tab.setIcon(it.iconId)
            }
        }.attach()
    }

    companion object {
        fun newInstance() = WeatherFragment()
    }
}