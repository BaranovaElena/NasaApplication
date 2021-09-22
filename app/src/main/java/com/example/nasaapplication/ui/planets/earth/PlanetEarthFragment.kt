package com.example.nasaapplication.ui.planets.earth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.nasaapplication.NasaApplication
import com.example.nasaapplication.R
import com.example.nasaapplication.databinding.FragmentPlanetEarthBinding
import com.google.android.material.snackbar.Snackbar

class PlanetEarthFragment : Fragment(R.layout.fragment_planet_earth) {
    private val binding: FragmentPlanetEarthBinding by viewBinding(
        FragmentPlanetEarthBinding::bind
    )
    private lateinit var viewModel: PlanetEarthViewModel
    private val app by lazy { requireActivity().application as NasaApplication }

    companion object {
        fun newInstance() = PlanetEarthFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlanetEarthViewModel::class.java)
        viewModel.initViewModel(app.earthPhotoRepo, app.earthPhotoService, app.apiKey)
        viewModel.loadStateLiveData.observe(viewLifecycleOwner, { onDataLoaded(it) })

        viewModel.onViewCreated()
    }

    private fun onDataLoaded(state: EarthPictureLoadState) {
        when (state) {
            is EarthPictureLoadState.Success -> {
                binding.earthPhotoCaption.text = state.caption
                binding.earthPhotoDateTime.text = state.date
                Glide.with(this)
                    .load(state.imagePath)
                    .centerCrop()
                    .into(binding.earthPhotoImageView)
            }
            is EarthPictureLoadState.Loading -> {
            }
            is EarthPictureLoadState.Error -> {
                showSnackbar(
                    when (state.error) {
                        EarthPictureLoadError.LOAD_ERROR -> getString(R.string.load_error)
                        EarthPictureLoadError.SERVER_ERROR -> getString(R.string.server_error)
                    }
                )
            }
        }
    }

    private fun showSnackbar(error: String) {
        Snackbar.make(binding.earthPhotoRootLayout, error, Snackbar.LENGTH_SHORT)
            .setAction(getString(R.string.snack_bar_action_text)) {
                viewModel.reloadClicked()
            }
            .show()
    }
}