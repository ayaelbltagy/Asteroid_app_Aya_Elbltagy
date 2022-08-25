package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Transformations.map
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AteroidObjectClass
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.AsteroidEntity
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

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

    // navigation from online
    private var _navigateToSelectedProperty = MutableLiveData<Asteroid>()

    val navigateToSelectedProperty: LiveData<Asteroid>
        get() = _navigateToSelectedProperty

    fun displayPropertyDetails(asteroid: Asteroid) {
        _navigateToSelectedProperty.value = asteroid
    }
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
    // navigation from db
    private val _navigateToDetails = MutableLiveData<AsteroidEntity>()

    val navigateToDetails: LiveData<AsteroidEntity>
        get() = _navigateToDetails

    fun doneNavigating() {
        _navigateToDetails.value = null
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


    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val startDate = sdf.format(Date())

    init {

        getPictureOfDay2()
        uiScope.launch {
            Asteroids = getAsteroidFromDatabase()
            oneAsteroid = getOneAsteroidFromDatabase(0L)
            refreshAsteroidsgetFromAPI()
        }
    }


    private val _image = MutableLiveData<PictureOfDay>()
    val image: LiveData<PictureOfDay>
        get() = _image

    private fun getPictureOfDay2() {
        uiScope.launch {
            try {
                _image.value = getPictureOfDay()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getPictureOfDay(): PictureOfDay {
        lateinit var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureOfDay = AteroidObjectClass.getPictureOfDay()
        }
        return pictureOfDay
    }

    private val _localList = MutableLiveData<List<AsteroidEntity>>()
    val localList: MutableLiveData<List<AsteroidEntity>>
        get() = _localList

    private val _refreshedList = MutableLiveData<List<Asteroid>>()
    val refreshedList: MutableLiveData<List<Asteroid>>
        get() = _refreshedList


    private fun refreshAsteroidsgetFromAPI() {
        viewModelScope.launch {
            val asteroids = AteroidObjectClass.getAsteroids()
            _refreshedList .value = asteroids
            _localList.value = asteroids.asAsteroidToEntities()
           // dao.addAsteroid(asteroids) // should handle it to add in db
        }

    }





    // will called from ui
    fun getAllAsteroid() {
        uiScope.launch {
            val asteroid = AsteroidEntity(0L,"","",0.0,0.0,0.0,0.0,false)
            Asteroids = getAsteroidFromDatabase()
        }
    }

    // will called from ui
    fun getOneAsteroid(id: Long) {
        uiScope.launch {
            oneAsteroid = getOneAsteroidFromDatabase(id)
            _navigateToDetails.value = oneAsteroid.value



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
            val asteroid = AsteroidEntity(0L,"","",0.0,0.0,0.0,0.0,false)
            insert(asteroid)
            _navigateToDetails.value = asteroid
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

    fun LiveData<AsteroidEntity>.asEntitiesToAsteroid() : LiveData<Asteroid> {
        return map {
            Asteroid(
                id = it.id,
                codename = it.codename,
                closeApproachDate = it.closeApproachDate,
                absoluteMagnitude = it.absoluteMagnitude,
                estimatedDiameter = it.estimatedDiameter,
                relativeVelocity = it.relativeVelocity,
                distanceFromEarth = it.distanceFromEarth,
                isPotentiallyHazardous = it.isPotentiallyHazardous
            )
        }
    }

    fun List<Asteroid>.asAsteroidToEntities() : List<AsteroidEntity> {
        return map {
            AsteroidEntity(
                id = it.id,
                codename = it.codename,
                closeApproachDate = it.closeApproachDate,
                absoluteMagnitude = it.absoluteMagnitude,
                estimatedDiameter = it.estimatedDiameter,
                relativeVelocity = it.relativeVelocity,
                distanceFromEarth = it.distanceFromEarth,
                isPotentiallyHazardous = it.isPotentiallyHazardous
            )
        }
    }
}