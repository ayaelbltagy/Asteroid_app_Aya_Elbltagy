package com.udacity.asteroidradar.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.main.AsteroidViewModel

class AsteroidViewModelFactory(private val dao: AsteroidDao, private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AsteroidViewModel::class.java)) {
            return AsteroidViewModel(application, dao) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}