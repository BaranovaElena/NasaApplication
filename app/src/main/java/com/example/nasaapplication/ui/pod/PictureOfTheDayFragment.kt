package com.example.nasaapplication.ui.pod

import android.graphics.Typeface.BOLD
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.nasaapplication.NasaApplication
import com.example.nasaapplication.R
import com.example.nasaapplication.databinding.PictureOfTheDayFragmentBinding
import com.example.nasaapplication.ui.LoadPodError
import com.example.nasaapplication.ui.showSnackBar
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class PictureOfTheDayFragment : Fragment(R.layout.picture_of_the_day_fragment) {
    private val binding: PictureOfTheDayFragmentBinding by viewBinding(
        PictureOfTheDayFragmentBinding::bind
    )

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

    private lateinit var viewModel: PictureOfTheDayViewModel
    private var chipState: Boolean = false

    private val app by lazy { requireActivity().application as NasaApplication }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()

        viewModel = ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
        viewModel.initViewModel(app.podRepo, app.podService, app.apiKey)
        viewModel.loadStateLiveData.observe(viewLifecycleOwner, { onPodLoaded(it) })
        viewModel.picturePositionLiveData.observe(viewLifecycleOwner, {onPicturePositionChanged(it)})

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
        if (!chipState) {
            binding.podChipGroup.check(R.id.pod_chip_today)
            chipState = true
        }
    }

    private fun onPodLoaded(state: LoadPodState) {
        when (state) {
            is LoadPodState.Success -> {
                binding.podPictureImageView.transitionName = state.picture

                setColoredText(binding.podTitleTextView, state.title)
                setColoredText(binding.podDescriptionTextView, state.description)

                Glide.with(this)
                    .load(state.picture)
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
                    .into(binding.podPictureImageView)

                binding.podPictureImageView.setOnClickListener {
                    viewModel.onImageClicked(state.picture)
                }
            }
            is LoadPodState.Loading -> {
            }
            is LoadPodState.Error -> {
                startPostponedEnterTransition()
                binding.podRootLayout.showSnackBar(
                    when (state.error) {
                        LoadPodError.LOAD_ERROR -> getString(R.string.load_error)
                        LoadPodError.SERVER_ERROR -> getString(R.string.server_error)
                        LoadPodError.NOT_IMAGE_ERROR -> getString(R.string.not_image_error)
                    },
                    getString(R.string.snack_bar_action_text),
                    { viewModel.reloadClicked() }
                )
            }
        }
    }

    private fun setColoredText(view: TextView, text: String) {
        val spannable = SpannableString(text)
        for (i in text.indices) {
            if (text[i].isDigit()) {
                spannable.setSpan(StyleSpan(BOLD), i, i+1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            } else if (text[i].isUpperCase()) {
                spannable.setSpan(
                    context?.getColor(R.color.pod_upper_text_color)?.let { ForegroundColorSpan(it) },
                    i,
                    i+1,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                )
                spannable.setSpan(StyleSpan(BOLD), i, i+1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            }
        }
        view.text = spannable
    }

    private fun onPicturePositionChanged(picture: String?) {
        picture?.let {
            (requireActivity() as Controller).showFullScreenPicture(picture, binding.podPictureImageView)
            viewModel.onFullScreenCommandSend()
        }
    }

    interface Controller {
        fun showFullScreenPicture(pictureUrl: String,  view: View)
    }
}

class Days {
    companion object {
        const val TODAY = 0
        const val YESTERDAY = -1
        const val DAY_BEFORE_YESTERDAY = -2
    }
}