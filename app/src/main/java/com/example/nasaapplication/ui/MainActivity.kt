package com.example.nasaapplication.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nasaapplication.R
import com.example.nasaapplication.databinding.ActivityMainBinding
import com.example.nasaapplication.ui.pod.PictureOfTheDayFragment
import com.example.nasaapplication.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), SettingsFragment.Controller {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private var bottomNavigationView: BottomNavigationView? = null

    private val sharedPreferencesName = "settings"
    private val sharedValueNameTheme = "Theme"
    private var currentTheme = Themes.DEFAULT.ordinal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        if(sharedPreferences.contains(sharedValueNameTheme)) {
            currentTheme = sharedPreferences.getInt(sharedValueNameTheme, Themes.DEFAULT.ordinal)
            setAppTheme(currentTheme)
        }
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
                    .replace(R.id.fragment_container, SettingsFragment.newInstance(currentTheme))
                    .commitNow()
            }
        }
        return true
    }

    override fun saveTheme(theme: Int) {
        saveThemeInSharedPreferences(theme)

        setAppTheme(theme)
        recreate()
    }

    private fun setAppTheme(theme: Int) {
        this.setTheme(
            when(theme) {
                Themes.DEFAULT.ordinal -> R.style.Theme_NasaApplication
                Themes.INDIGO.ordinal -> R.style.Theme_NasaApplication_Indigo
                Themes.RED.ordinal -> R.style.Theme_NasaApplication_Red
                else -> R.style.Theme_NasaApplication
            }
        )
    }

    private fun saveThemeInSharedPreferences(theme: Int) {
        val sharedPreferences = getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(sharedValueNameTheme, theme)
        editor.apply()
    }
}

enum class Themes {
    DEFAULT, INDIGO, RED
}