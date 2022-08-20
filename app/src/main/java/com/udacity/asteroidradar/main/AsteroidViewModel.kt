package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
 import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.AsteroidEntity
import kotlinx.coroutines.*

class AsteroidViewModel(application : Application, val dao :AsteroidDao) : AndroidViewModel(application) {

    // create view model job and override on canclled() for canclleing co-rotuines when this view model is destroyed
    private var viewModelJob = Job()
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // define a scope for co-rotuines to run in it
    private val uiScope = CoroutineScope(Dispatchers.Main+viewModelJob)

    // create live data var and use co-rotinues to initilize it from database
    private lateinit var liveList : LiveData<List<AsteroidEntity>>

     private  var Asteroids = dao.getAsteroid()

    // list that will passed to ui controller
    val dataList: LiveData<List<AsteroidEntity>>
        get() = Asteroids

    // to refresh data after navigation we should tell view model that navigation is done
    private val _navigationDone = MutableLiveData<AsteroidEntity>()
    val navigationDone : LiveData<AsteroidEntity>
    get() = _navigationDone

    fun doneNavigation(){
        _navigationDone.value = null
    }

    init {

        uiScope.launch { liveList = getAsteroidFromDatabase() }
    }

    private suspend fun getAsteroidFromDatabase(): LiveData<List<AsteroidEntity>>{
        return withContext(Dispatchers.IO){
            var Asteroids = dao.getAsteroid()
            Asteroids
        }
    }


   // add function that will insert data to database
     fun onAddNewAsteroid (){
        uiScope.launch {
            val asteroid = AsteroidEntity(200,2.0,30.0)
            insert(asteroid)
            liveList = getAsteroidFromDatabase()
        }
    }
    private suspend fun insert (asteroid : AsteroidEntity){
       withContext(Dispatchers.IO){
            dao.addAsteroid(asteroid)
        }
    }
}