package com.example.nasaapplication.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nasaapplication.R
import com.example.nasaapplication.databinding.FragmentSettingsBinding
import com.example.nasaapplication.ui.Themes

class SettingsFragment : Fragment() {
    private val binding: FragmentSettingsBinding by viewBinding(
        FragmentSettingsBinding::bind
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    companion object {
        const val BUNDLE_EXTRA_KEY = "THEME_BUNDLE_EXTRA_KEY"

        fun newInstance(theme: Int): SettingsFragment {
            val fragment = SettingsFragment()
            val args = Bundle()
            args.putInt(BUNDLE_EXTRA_KEY, theme)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.settingsThemesRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val newTheme = when (checkedId) {
                R.id.settings_themes_radio_indigo -> Themes.INDIGO.ordinal
                R.id.settings_themes_radio_red -> Themes.RED.ordinal
                else -> Themes.DEFAULT.ordinal
            }

            if (newTheme != arguments?.getInt(BUNDLE_EXTRA_KEY))
                setNewTheme(checkedId)
        }

        binding.settingsThemesRadioGroup.check(
            when (arguments?.getInt(BUNDLE_EXTRA_KEY)) {
                Themes.DEFAULT.ordinal -> R.id.settings_themes_radio_default
                Themes.INDIGO.ordinal -> R.id.settings_themes_radio_indigo
                Themes.RED.ordinal -> R.id.settings_themes_radio_red
                else -> R.id.settings_themes_radio_default
            }
        )
    }

    private fun setNewTheme(checkedId: Int) {
        (requireActivity() as Controller).removeSettingsFragmentFromBackStack()
        when (checkedId) {
            R.id.settings_themes_radio_default ->
                (requireActivity() as Controller).saveTheme(Themes.DEFAULT.ordinal)
            R.id.settings_themes_radio_indigo ->
                (requireActivity() as Controller).saveTheme(Themes.INDIGO.ordinal)
            R.id.settings_themes_radio_red ->
                (requireActivity() as Controller).saveTheme(Themes.RED.ordinal)
        }
    }

    interface Controller {
        fun saveTheme(theme: Int)
        fun removeSettingsFragmentFromBackStack()
    }
}