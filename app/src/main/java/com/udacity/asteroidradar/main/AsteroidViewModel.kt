package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.api.ServerApi
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.models.AsteroidListModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response

class AsteroidViewModel(application: Application, val dao: AsteroidDao) :
    AndroidViewModel(application) {
    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by view model
     */

    // Job is holder that catch coroutines
    // create  job and override on canclled() for canclleing coroutines when this view model is destroyed
    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _navigateToSleepQuality = MutableLiveData<AsteroidEntity>()

    val navigateToSleepQuality: LiveData<AsteroidEntity>
        get() = _navigateToSleepQuality

    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    // define a scope for coroutines to run in it
    // Dispatchers.Main (work all over the project and used for update live data)
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // initial value
    private var Asteroids = dao.getAsteroid()


    // initial value
    private var oneAsteroid = dao.getAsteroidWithId(0L)


    // list that will passed to ui controller
    val dataList: LiveData<List<AsteroidEntity>>
        get() = Asteroids

    // list that will passed to ui controller
    val asteroid: LiveData<AsteroidEntity>
        get() = oneAsteroid

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the request status String
    val response: LiveData<String>
        get() = _response

    init {
        getMarsRealEstateProperties()
//
//        uiScope.launch {
//            Asteroids = getAsteroidFromDatabase()
//            oneAsteroid = getOneAsteroidFromDatabase(0L)
//
//        }
    }

    private fun getMarsRealEstateProperties() {
        ServerApi.retrofitService.getProperties()
            .enqueue(object : retrofit2.Callback<List<AsteroidListModel>> {
                override fun onResponse(
                    call: Call<List<AsteroidListModel>>,
                    response: Response<List<AsteroidListModel>>
                ) {
                    _response.value = response.body()?.toString()
                    Log.i("test",response.body()?.get(0).toString())
                }

                override fun onFailure(call: Call<List<AsteroidListModel>>, t: Throwable) {
                    _response.value = "Failure: " + t.message

                }
            })

    }

    // will called from ui
    fun getAllAsteroid() {
        uiScope.launch {
            val asteroid = AsteroidEntity()
            Asteroids = getAsteroidFromDatabase()
        }
    }

    // will called from ui
    fun getOneAsteroid(id: Long) {
        uiScope.launch {
            oneAsteroid = getOneAsteroidFromDatabase(id)
        }
    }

    private suspend fun getOneAsteroidFromDatabase(id: Long): LiveData<AsteroidEntity> {
        // to switch from main thread to IO thread use withContext
        // Dispatchers.IO used for get data from room database
        return withContext(Dispatchers.IO) {
            //this code work only in Dispatchers.IO thread and when finish coroutines will return to main thread
            var Asteroid = dao.getAsteroidWithId(id)
            Asteroid
        }
    }


    // will called from ui
    fun onAddNewAsteroid() {
        uiScope.launch {
            val asteroid = AsteroidEntity()
            insert(asteroid)
            _navigateToSleepQuality.value = asteroid
            Asteroids = getAsteroidFromDatabase()
        }
    }

    private suspend fun getAsteroidFromDatabase(): LiveData<List<AsteroidEntity>> {
        // to switch from main thread to IO thread use withContext
        // Dispatchers.IO used for get data from room database
        return withContext(Dispatchers.IO) {
            //this code work only in Dispatchers.IO thread and when finish coroutines will return to main thread
            var Asteroid = dao.getAsteroid()
            Asteroid
        }
    }


    private suspend fun insert(asteroid: AsteroidEntity) {
        withContext(Dispatchers.IO) {
            dao.addAsteroid(asteroid)
        }
    }

}