package com.udacity.asteroidradar.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding
import com.udacity.asteroidradar.helper.PreferenceHelper
import com.udacity.asteroidradar.helper.Utility

class DetailFragment : Fragment() {

    private lateinit var helper: PreferenceHelper


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        helper = PreferenceHelper(activity)

        val asteroid = DetailFragmentArgs.fromBundle(requireArguments()).selectedAsteroid

        binding.asteroid = asteroid

        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }
        if (helper.getLocal().equals("ar")) {
            Utility.setLocale(requireActivity().baseContext, "ar")
        } else if (helper.getLocal().equals("en")) {
            Utility.setLocale(requireActivity().baseContext, "en")
        } else {
            Utility.setLocale(requireActivity().baseContext, "en")
        }
        return binding.root
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }


}
