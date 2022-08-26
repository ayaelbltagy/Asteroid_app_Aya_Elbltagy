package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.AteroidObjectClass
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.factory.AsteroidViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

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
        val databaseSource = AsteroidDatabase.getInstance(application).getAsteroidDao()
        val viewModelFactory = AsteroidViewModelFactory(databaseSource, application)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(AsteroidViewModel::class.java)
        binding.viewModel = viewModel
//        // create adapter
//        var adapter = AsteroidAdapter(AsteroidClickListener { Id ->
//             viewModel.getOneAsteroid(Id) // get asteroid from room db
//        })
//        binding.asteroidRecycler.adapter = adapter
//        // add list to adapter
//        viewModel.localList.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                adapter.submitList(it)
//            }
//        })

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

            if(it != null){
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
        return true
    }
}