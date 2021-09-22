package com.example.nasaapplication.ui.planets

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nasaapplication.R
import com.example.nasaapplication.databinding.FragmentPlanetsBinding
import com.example.nasaapplication.ui.FragmentFactory
import com.example.nasaapplication.ui.NasaViewPagerAdapter
import com.example.nasaapplication.ui.planets.earth.PlanetEarthFragment
import com.example.nasaapplication.ui.planets.mars.PlanetMarsFragment
import com.google.android.material.tabs.TabLayoutMediator

class PlanetsFragment : Fragment(R.layout.fragment_planets) {
    private val binding: FragmentPlanetsBinding by viewBinding(FragmentPlanetsBinding::bind)

    companion object {
        fun newInstance() = PlanetsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragments = listOf(
            FragmentFactory(getString(R.string.planets_tab_earth_title), R.drawable.ic_earth) {
                PlanetEarthFragment.newInstance()
            },
            FragmentFactory(getString(R.string.planets_tab_mars_title), R.drawable.ic_mars) {
                PlanetMarsFragment.newInstance()
            }
        )

        with(binding.planetsViewPager) {
            adapter = NasaViewPagerAdapter(requireActivity(), fragments)
        }

        TabLayoutMediator(binding.planetsTabLayout, binding.planetsViewPager) {tab, pos ->
            fragments[pos].let {
                tab.text = it.title
                tab.setIcon(it.iconId)
            }
        }.attach()
    }
}