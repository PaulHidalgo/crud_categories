package com.phidalgo.crudcategories.ui.settings

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.phidalgo.crudcategories.R
import com.phidalgo.crudcategories.databinding.FragmentSettingsBinding
import java.util.Locale

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using DataBindingUtil
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar el estado del Switch para el modo oscuro
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        binding.switchDarkMode.isChecked = currentNightMode == Configuration.UI_MODE_NIGHT_YES
        updateModeText(currentNightMode == Configuration.UI_MODE_NIGHT_YES)

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                updateModeText(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                updateModeText(false)
            }
        }

        // Inicializar el estado del Switch para el idioma (supongamos que el idioma por defecto es inglés)
        val currentLanguage = Locale.getDefault().language
        binding.switchLanguage.isChecked = currentLanguage == "es"
        updateLanguageText(currentLanguage == "es")

        binding.switchLanguage.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setLocale("es")
                updateLanguageText(true)
            } else {
                setLocale("en")
                updateLanguageText(false)
            }
        }
    }

    private fun updateModeText(isDarkMode: Boolean) {
        binding.textModeSelection.text = if (isDarkMode) getString(R.string.mode_dark) else getString(R.string.mode_light)
    }

    private fun updateLanguageText(isSpanish: Boolean) {
        binding.textLanguageSelection.text = if (isSpanish) getString(R.string.language_spanish) else getString(R.string.language_english)
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        requireActivity().resources.updateConfiguration(config, requireActivity().resources.displayMetrics)
        requireActivity().recreate() // Recarga la actividad para aplicar los cambios
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
