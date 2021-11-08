package com.example.nasaapplication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.nasaapplication.databinding.FragmentFullScreenPictureBinding

class FullScreenPictureFragment : Fragment(R.layout.fragment_full_screen_picture) {
    private val binding: FragmentFullScreenPictureBinding by viewBinding(
        FragmentFullScreenPictureBinding::bind
    )

    private var pictureUrl: String? = null

    companion object {
        const val BUNDLE_EXTRA_KEY = "PICTURE_BUNDLE_EXTRA_KEY"

        fun newInstance(pictureUrl: String) =
            FullScreenPictureFragment().apply {
                arguments = Bundle().apply { putString(BUNDLE_EXTRA_KEY, pictureUrl) }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pictureUrl = arguments?.getString(BUNDLE_EXTRA_KEY)

        (requireActivity() as Controller).hideNavigation()

        binding.closeButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        Glide.with(this)
            .load(pictureUrl)
            .into(binding.mainImageView)
    }

    override fun onDestroy() {
        (requireActivity() as Controller).showNavigation()
        super.onDestroy()
    }

    interface Controller {
        fun hideNavigation()
        fun showNavigation()
    }
}