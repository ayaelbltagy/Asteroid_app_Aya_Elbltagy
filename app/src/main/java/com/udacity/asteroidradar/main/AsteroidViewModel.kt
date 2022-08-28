package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
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


    val repoImage = repo.pictureOfDayEntity

    val navigateToSelectedProperty: LiveData<Asteroid>
        get() = _navigateToSelectedProperty

    private val list = repo.asteroids

    val asteroidList: MediatorLiveData<List<Asteroid>> = MediatorLiveData()
    private val todayAsteroidList = repo.todayAsteroidList


    init {
        getPictureOfDay()
        getListOfDay()
        viewModelScope.launch {
            asteroidList.addSource(list) {
                asteroidList.value = it
            }

        }
    }


    fun onTodayAsteroidsClicked() {
        removeSource()
        asteroidList.addSource(todayAsteroidList) {
            asteroidList.value = it
        }

    }

    fun onViewWeekAsteroidsClicked() {
         removeSource()
        asteroidList.addSource(list) {
            asteroidList.value = it
        }

    }

    fun onSavedAsteroidsClicked() {
        removeSource()
        asteroidList.addSource(list) {
            asteroidList.value = it
        }

    }

    private fun removeSource() {
        asteroidList.removeSource(todayAsteroidList)
        asteroidList.removeSource(list)
    }


    private fun getPictureOfDay() {
        viewModelScope.launch {
            try {
                repo.getPic()

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