package com.example.nasaapplication.ui.planets.mars

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.nasaapplication.NasaApplication
import com.example.nasaapplication.R
import com.example.nasaapplication.databinding.FragmentPlanetMarsBinding
import com.google.android.material.snackbar.Snackbar

class PlanetMarsFragment : Fragment(R.layout.fragment_planet_mars) {
    private val binding: FragmentPlanetMarsBinding by viewBinding(
        FragmentPlanetMarsBinding::bind
    )
    private lateinit var viewModel: PlanetMarsViewModel
    private val app by lazy { requireActivity().application as NasaApplication }

    companion object {
        fun newInstance() = PlanetMarsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlanetMarsViewModel::class.java)
        viewModel.initViewModel(app.marsPhotoRepo, app.marsPhotoService, app.apiKey)
        viewModel.loadStateLiveData.observe(viewLifecycleOwner, { onDataLoaded(it) })

        viewModel.onViewCreated()
    }

    private fun onDataLoaded(state: MarsPictureLoadState) {
        when (state) {
            is MarsPictureLoadState.Success -> {
                binding.planetMarsSol.text = state.sol
                binding.planetMarsDate.text = state.date
                Glide.with(this)
                    .load(state.imagePath)
                    .centerCrop()
                    .into(binding.planetMarsImageView)
            }
            is MarsPictureLoadState.Loading -> {
            }
            is MarsPictureLoadState.Error -> {
                showSnackbar(
                    when (state.error) {
                        MarsPictureLoadError.LOAD_ERROR -> getString(R.string.load_error)
                        MarsPictureLoadError.SERVER_ERROR -> getString(R.string.server_error)
                    }
                )
            }
        }
    }

    private fun showSnackbar(error: String) {
        Snackbar.make(binding.planetMarsRootLayout, error, Snackbar.LENGTH_SHORT)
            .setAction(getString(R.string.snack_bar_action_text)) {
                viewModel.reloadClicked()
            }
            .show()
    }
}