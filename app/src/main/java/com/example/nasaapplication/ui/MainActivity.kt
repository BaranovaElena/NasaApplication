package com.example.nasaapplication.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nasaapplication.ui.planets.PlanetsFragment
import com.example.nasaapplication.R
import com.example.nasaapplication.ui.weather.WeatherFragment
import com.example.nasaapplication.databinding.ActivityMainBinding
import com.example.nasaapplication.ui.pod.PictureOfTheDayFragment
import com.example.nasaapplication.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val SHARED_PREFERENCES_NAME = "settings"

class MainActivity : AppCompatActivity(), SettingsFragment.Controller {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private var bottomNavigationView: BottomNavigationView? = null

    private lateinit var sharedPreferences: SharedPreferences
    private val sharedValueNameTheme = "Theme"
    private var currentTheme = Themes.DEFAULT.ordinal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        if (sharedPreferences.contains(sharedValueNameTheme)) {
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
            R.id.nav_weather -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, WeatherFragment.newInstance())
                    .commitNow()
            }
            R.id.nav_planets -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, PlanetsFragment.newInstance())
                    .commitNow()
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.app_bar_settings -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, SettingsFragment.newInstance(currentTheme))
                    .addToBackStack(null)
                    .commit()
                true
            }
            else -> false
        }
    }

    override fun saveTheme(theme: Int) {
        saveThemeInSharedPreferences(theme)

        setAppTheme(theme)
        recreate()
    }

    override fun removeSettingsFragmentFromBackStack() {
        supportFragmentManager.popBackStack()
    }

    private fun setAppTheme(theme: Int) {
        this.setTheme(
            when (theme) {
                Themes.DEFAULT.ordinal -> R.style.Theme_NasaApplication
                Themes.INDIGO.ordinal -> R.style.Theme_NasaApplication_Indigo
                Themes.RED.ordinal -> R.style.Theme_NasaApplication_Red
                else -> R.style.Theme_NasaApplication
            }
        )
    }

    private fun saveThemeInSharedPreferences(theme: Int) {
        sharedPreferences.edit().apply {
            putInt(sharedValueNameTheme, theme)
            apply()
        }
    }
}

enum class Themes {
    DEFAULT, INDIGO, RED
}