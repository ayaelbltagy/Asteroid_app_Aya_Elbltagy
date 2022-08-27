package com.udacity.asteroidradar.main

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
import com.udacity.asteroidradar.helper.Utility

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var helper: PreferenceHelper
    private lateinit var viewModel: AsteroidViewModel

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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AsteroidViewModel::class.java)
        binding.viewModel = viewModel
        // setup your adapter
        var adapter = MainAsteroidAdapter(MainAsteroidClickListener {
            viewModel.displayPropertyDetails(it)
        })
        // show dialog till api get response
        binding.statusLoadingWheel.visibility = View.VISIBLE
        binding.asteroidRecycler.adapter = adapter
        viewModel.asteroidList.observe(viewLifecycleOwner, Observer {
            it?.let {
                // hide dialog as list is ready
                binding.statusLoadingWheel.visibility = View.GONE
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
                Utility.setLocale(requireActivity().baseContext, "en")
                requireActivity().recreate()
            } else if (helper.getLocal().equals("en")) {
                Utility.setLocale(requireActivity().baseContext, "ar")
                requireActivity().recreate()
            } else {
                Utility.setLocale(requireActivity().baseContext, "en")
                requireActivity().recreate()
            }
        } else if (id == R.id.show_week) {
            viewModel.onViewWeekAsteroidsClicked()
        } else if (id == R.id.show_today) {
            viewModel.onTodayAsteroidsClicked()
        } else if (id == R.id.show_saved) {
            viewModel.onSavedAsteroidsClicked()
        }

        return super.onOptionsItemSelected(item)
    }


}