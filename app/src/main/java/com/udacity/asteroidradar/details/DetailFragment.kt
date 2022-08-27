package com.udacity.asteroidradar.details


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding
import com.udacity.asteroidradar.helper.PreferenceHelper
import java.util.*

class DetailFragment : Fragment() {

    private lateinit var helper: PreferenceHelper


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        helper = PreferenceHelper(activity)

        val asteroid = DetailFragmentArgs.fromBundle(requireArguments()).selectedAsteroid

        binding.asteroid = asteroid

        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }
        if (helper.getLocal().equals("ar")) {
            setLocale(requireActivity().baseContext, "ar")
        } else if (helper.getLocal().equals("en")) {
            setLocale(requireActivity().baseContext, "en")
        } else {
            setLocale(requireActivity().baseContext, "en")
        }
        return binding.root
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(activity!!)
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }

    fun setLocale(context: Context, languageCode: String) {
        helper.setLocal(languageCode)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResoureces(context, languageCode)
        }
        return updateResoureces(context, languageCode)

    }

    fun updateResoureces(context: Context, languageCode: String) {
        var local =  Locale(languageCode.toLowerCase())
        Locale.setDefault(local)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = local
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}
