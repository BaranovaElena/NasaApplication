package com.example.nasaapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nasaapplication.R
import com.example.nasaapplication.ui.settings.SettingsFragment
import com.example.nasaapplication.databinding.ActivityMainBinding
import com.example.nasaapplication.ui.pod.PictureOfTheDayFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView?.setOnItemSelectedListener { item -> setBottomNavListener(item) }
        bottomNavigationView?.selectedItemId = R.id.nav_pod
    }

    private fun setBottomNavListener(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_pod -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, PictureOfTheDayFragment.newInstance())
                    .commitNow()
            }
            R.id.nav_settings -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SettingsFragment.newInstance())
                    .commitNow()
            }
        }
        return true
    }
}