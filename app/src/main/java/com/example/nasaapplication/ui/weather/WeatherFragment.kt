package com.example.nasaapplication.ui.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nasaapplication.R
import com.example.nasaapplication.databinding.FragmentWeatherBinding
import com.example.nasaapplication.ui.weather.mars.MarsWeatherFragment
import com.example.nasaapplication.ui.weather.space.SpaceWeatherFragment
import com.google.android.material.tabs.TabLayoutMediator

class WeatherFragment : Fragment() {
    private val binding: FragmentWeatherBinding by viewBinding(FragmentWeatherBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

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
            adapter = WeatherViewPagerAdapter(requireActivity(), fragments)
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