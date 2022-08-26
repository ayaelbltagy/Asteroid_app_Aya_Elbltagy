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
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        // option menu
        setHasOptionsMenu(true)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = AsteroidViewModelFactory(application)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(AsteroidViewModel::class.java)
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
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val startDate = sdf.format(Date())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}