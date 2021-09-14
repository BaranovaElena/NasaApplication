package com.example.nasaapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nasaapplication.R
import com.example.nasaapplication.ui.pod.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, PictureOfTheDayFragment.newInstance())
            .commitNow()
    }
}