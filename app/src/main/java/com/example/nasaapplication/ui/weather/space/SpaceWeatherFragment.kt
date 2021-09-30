package com.example.nasaapplication.ui.weather.space

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nasaapplication.NasaApplication
import com.example.nasaapplication.R
import com.example.nasaapplication.databinding.FragmentSpaceWeatherBinding
import com.example.nasaapplication.ui.LoadError
import com.example.nasaapplication.ui.showSnackBar

class SpaceWeatherFragment : Fragment(R.layout.fragment_space_weather) {
    private val binding: FragmentSpaceWeatherBinding by viewBinding(
        FragmentSpaceWeatherBinding::bind
    )
    private lateinit var viewModel: SpaceWeatherViewModel
    private val app by lazy { requireActivity().application as NasaApplication }

    companion object {
        fun newInstance() = SpaceWeatherFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SpaceWeatherViewModel::class.java)
        viewModel.initViewModel(app.spaceWeatherRepo, app.spaceWeatherService, app.apiKey)
        viewModel.loadStateLiveData.observe(viewLifecycleOwner, { onWeatherLoaded(it) })

        viewModel.onViewCreated()
    }

    private fun onWeatherLoaded(state: SpaceWeatherLoadState) {
        when (state) {
            is SpaceWeatherLoadState.Success -> {
                binding.spaceWeatherMsgType.text = state.messageType
                binding.spaceWeatherIssueTime.text = state.messageIssueTime
                binding.spaceWeatherMsgBody.text = state.messageBody
            }
            is SpaceWeatherLoadState.Loading -> {
            }
            is SpaceWeatherLoadState.Error -> {
                binding.spaceWeatherRootLayout.showSnackBar(
                    when (state.error) {
                        LoadError.LOAD_ERROR -> getString(R.string.load_error)
                        LoadError.SERVER_ERROR -> getString(R.string.server_error)
                    },
                    getString(R.string.snack_bar_action_text),
                    { viewModel.reloadClicked() }
                )
            }
        }
    }
}