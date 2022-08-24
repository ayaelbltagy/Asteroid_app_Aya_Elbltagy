package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.API_KEY
import com.udacity.asteroidradar.api.ServerApi
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.models.ModelResponse
import com.udacity.asteroidradar.models.ResponseModel
import com.udacity.asteroidradar.models.moResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.await
import java.lang.Exception
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
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status String
    val status: LiveData<String>
        get() = _status
    private val _property = MutableLiveData<List<moResponse>>()
    val property: LiveData<List<moResponse>>
        get() = _property

    private val _image = MutableLiveData<PictureOfDay>()
    val image: LiveData<PictureOfDay>
        get() = _image

    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val startDate = sdf.format(Date())

    init {
      //  getListOfAsteroid("","",API_KEY)
        getData()
        getImage()
        uiScope.launch {
            Asteroids = getAsteroidFromDatabase()
            oneAsteroid = getOneAsteroidFromDatabase(0L)

        }
    }

    private  fun getData(){
        viewModelScope.launch {

                var listResult = ServerApi.retrofitService.getList( "","",API_KEY)
            try {
                var list = listResult
                if(list.size>0){
                    _property.value = list
                }
            }catch (e :Exception){
                _status.value = "Failed :${e.message}"
            }

        }
    }
    private fun getImage(){
        viewModelScope.launch {
            try {
                _image.value = ServerApi.retrofitService.getPictureOfDay(API_KEY)
            } catch (e: Exception) {
                Log.i("test","Failed :${e.message}")
            }
        }

    }

//     private fun getListOfAsteroid(start_date:String, end_date:String,api_key:String) {
//        ServerApi.retrofitService.getList(start_date,end_date,api_key)
//            .enqueue(object : retrofit2.Callback<List<ResponseModel>> {
//                override fun onResponse(
//                    call: Call<List<ResponseModel>>,
//                    response: Response<List<ResponseModel>>
//                ) {
//                    _response.value = response.body()?.toString()
//                 }
//
//                override fun onFailure(call: Call<List<ResponseModel>>, t: Throwable) {
//                    _response.value = "Failure: " + t.message
//
//                }
//            })
//
//    }

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