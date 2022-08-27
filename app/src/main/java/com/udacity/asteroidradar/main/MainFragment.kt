package com.udacity.asteroidradar.main

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.factory.AsteroidViewModelFactory
import com.udacity.asteroidradar.helper.PreferenceHelper
import java.util.*

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var helper: PreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        helper = PreferenceHelper(activity)
        // option menu
        setHasOptionsMenu(true)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = AsteroidViewModelFactory(application)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(AsteroidViewModel::class.java)
        binding.viewModel = viewModel


        var adapter = MainAsteroidAdapter(MainAsteroidClickListener {
            viewModel.displayPropertyDetails(it)
        })
        binding.asteroidRecycler.adapter = adapter
        viewModel.list.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {

            if (it != null) {
                // navigate to details
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item!!.itemId
        if (id == R.id.lang) {
            if (helper.getLocal().equals("ar")) {
                setLocale(requireActivity().baseContext, "en")
            } else if (helper.getLocal().equals("en")) {
                setLocale(requireActivity().baseContext, "ar")
            } else {
                setLocale(requireActivity().baseContext, "en")
            }

        }

        return super.onOptionsItemSelected(item)
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
        requireActivity().recreate()
    }


}