package com.example.nasaapplication.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class NasaViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val fragments: List<FragmentFactory> = emptyList()
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position].factoryMethod()

}