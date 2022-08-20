package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.factory.AsteroidViewModelFactory

class MainFragment : Fragment() {


    private lateinit var binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        // val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this



        setHasOptionsMenu(true)
        val application = requireNotNull(this.activity).application
        val databaseSource = AsteroidDatabase.getInstance(application).getAsteroidDao()
        val viewModelFactory = AsteroidViewModelFactory(databaseSource, application)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(AsteroidViewModel::class.java)
        binding.viewModel = viewModel
        // get data from view model
        viewModel.dataList.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it?.get(0)?.absolute_magnitude.toString(), Toast.LENGTH_LONG).show()
        })
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
