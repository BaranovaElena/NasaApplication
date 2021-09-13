package com.example.nasaapplication.ui.pod

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.nasaapplication.R
import com.example.nasaapplication.databinding.PictureOfTheDayFragmentBinding

class PictureOfTheDayFragment : Fragment() {
    private val binding: PictureOfTheDayFragmentBinding by viewBinding(
        PictureOfTheDayFragmentBinding::bind
    )

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

    private lateinit var viewModel: PictureOfTheDayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.picture_of_the_day_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
        viewModel.loadStateLiveData.observe(viewLifecycleOwner, { onPodLoaded(it) })

        viewModel.onViewCreated()
    }

    private fun onPodLoaded(state: LoadPodState) {
        when (state) {
            is LoadPodState.Success -> {
                binding.podTitle.text = state.title
                binding.podDescription.text = state.description

                Glide.with(this)
                    .load(state.picture)
                    .centerCrop()
                    .into(binding.podImageView)
            }
            is LoadPodState.Loading -> {

            }
            is LoadPodState.Error -> {
                Toast.makeText(context, state.error.message,Toast.LENGTH_SHORT).show()
            }
        }
    }

}