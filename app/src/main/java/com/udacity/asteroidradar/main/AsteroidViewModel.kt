package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidDatabase.Companion.getInstance
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AsteroidViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by view model
     */


    // navigation from online
    private var _navigateToSelectedProperty = MutableLiveData<Asteroid>()

    private val database = getInstance(application)

    private val repo = AsteroidRepository(database)

    private val _image = MutableLiveData<PictureOfDay>()
    val image: LiveData<PictureOfDay>
        get() = _image


    val navigateToSelectedProperty: LiveData<Asteroid>
        get() = _navigateToSelectedProperty

    val list = repo.asteroids


    init {
        getPictureOfDay()
        getListOfDay()
    }


    private fun getPictureOfDay() {
        viewModelScope.launch {
            try {
                _image.value = repo.getPictureOfDay()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getListOfDay() {
        viewModelScope.launch {
            repo.refreshedAsteroid()
        }
    }


    fun displayPropertyDetails(asteroid: Asteroid) {
        _navigateToSelectedProperty.value = asteroid
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

}