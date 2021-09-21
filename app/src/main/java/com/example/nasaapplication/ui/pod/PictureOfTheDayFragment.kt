package com.example.nasaapplication.ui.pod

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.nasaapplication.NasaApplication
import com.example.nasaapplication.R
import com.example.nasaapplication.databinding.PictureOfTheDayFragmentBinding
import com.google.android.material.snackbar.Snackbar

class PictureOfTheDayFragment : Fragment(R.layout.picture_of_the_day_fragment) {
    private val binding: PictureOfTheDayFragmentBinding by viewBinding(
        PictureOfTheDayFragmentBinding::bind
    )

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

    private lateinit var viewModel: PictureOfTheDayViewModel

    private val app by lazy { requireActivity().application as NasaApplication }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
        viewModel.initViewModel(app.podRepo, app.service, app.apiKey)
        viewModel.loadStateLiveData.observe(viewLifecycleOwner, { onPodLoaded(it) })

        binding.podChipGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.onDayChipChanged(
                when (checkedId) {
                    R.id.pod_chip_today -> Days.TODAY
                    R.id.pod_chip_yesterday -> Days.YESTERDAY
                    R.id.pod_chip_before_yesterday -> Days.DAY_BEFORE_YESTERDAY
                    else -> Days.TODAY
                }
            )
        }
        binding.podChipGroup.check(R.id.pod_chip_today)
    }

    private fun onPodLoaded(state: LoadPodState) {
        when (state) {
            is LoadPodState.Success -> {
                binding.podTitleTextView.text = state.title
                binding.podDescriptionTextView.text = state.description

                Glide.with(this)
                    .load(state.picture)
                    .centerCrop()
                    .into(binding.podPictureImageView)
            }
            is LoadPodState.Loading -> {
            }
            is LoadPodState.Error -> {
                showSnackbar(
                    when (state.error) {
                        LoadPodError.LOAD_ERROR -> getString(R.string.load_error)
                        LoadPodError.SERVER_ERROR -> getString(R.string.server_error)
                        LoadPodError.NOT_IMAGE_ERROR -> getString(R.string.not_image_error)
                    }
                )
            }
        }
    }

    private fun showSnackbar(error: String) {
        Snackbar.make(binding.podRootLayout, error, Snackbar.LENGTH_SHORT)
            .setAction(getString(R.string.snack_bar_action_text)) {
                viewModel.reloadClicked()
            }
            .show()
    }
}

class Days {
    companion object {
        const val TODAY = 0
        const val YESTERDAY = -1
        const val DAY_BEFORE_YESTERDAY = -2
    }
}