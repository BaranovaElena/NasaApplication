package com.example.nasaapplication.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.transition.Transition
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.nasaapplication.databinding.FragmentFullScreenPictureBinding
import androidx.transition.TransitionInflater
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.nasaapplication.R

class FullScreenPictureFragment : Fragment(R.layout.fragment_full_screen_picture) {
    private val binding: FragmentFullScreenPictureBinding by viewBinding(
        FragmentFullScreenPictureBinding::bind
    )

    companion object {
        const val BUNDLE_EXTRA_KEY = "PICTURE_BUNDLE_EXTRA_KEY"

        fun newInstance(pictureUrl: String) =
            FullScreenPictureFragment().apply {
                arguments = Bundle().apply { putString(BUNDLE_EXTRA_KEY, pictureUrl) }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pictureUrl = arguments?.getString(BUNDLE_EXTRA_KEY)

        binding.closeButton.visibility = View.INVISIBLE
        binding.closeButton.setOnClickListener {
            (requireActivity() as Controller).closeFullScreen()
        }

        (requireActivity() as Controller).hideNavigation()
        sharedElementEnterTransition =
            TransitionInflater
                .from(context)
                .inflateTransition(R.transition.inflate_transition)
                .addListener(object : Transition.TransitionListener {
                    override fun onTransitionStart(transition: Transition) {}
                    override fun onTransitionCancel(transition: Transition) {}
                    override fun onTransitionPause(transition: Transition) {}
                    override fun onTransitionResume(transition: Transition) {}

                    override fun onTransitionEnd(transition: Transition) {
                        if (this@FullScreenPictureFragment.isResumed)
                            binding.closeButton.visibility = View.VISIBLE
                    }
                })

        if (savedInstanceState == null) {
            postponeEnterTransition()
        }
        binding.fullScreenImageView.transitionName = pictureUrl

        Glide.with(this)
            .load(pictureUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?, model: Any?,
                    target: Target<Drawable>?, isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?, model: Any?, target: Target<Drawable>?,
                    dataSource: DataSource?, isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(binding.fullScreenImageView)
    }

    override fun onDestroy() {
        (requireActivity() as Controller).showNavigation()
        super.onDestroy()
    }

    interface Controller {
        fun hideNavigation()
        fun showNavigation()
        fun closeFullScreen()
    }
}