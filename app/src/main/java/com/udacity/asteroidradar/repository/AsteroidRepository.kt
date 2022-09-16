package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import com.squareup.moshi.Moshi
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AteroidObjectClass
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.database.PictureOfDayEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        map(database.getAsteroidDao().getAsteroid()) {
            it.asDomainModel()
        }

    val todayAsteroidList: LiveData<List<Asteroid>> =
        map(database.getAsteroidDao().getTodayAsteroid(Constants.getCurrentDate())) {
            it.asDomainModel()
        }


    suspend fun refreshedAsteroid() {
        withContext(Dispatchers.IO) {
            val asteroids = AteroidObjectClass.getAsteroids()
            database.getAsteroidDao().addAsteroid(asteroids.asAsteroidToEntities())
        }
    }


    val pictureOfDayEntity: LiveData<PictureOfDayEntity>
        get() = database.getAsteroidDaoPic().get()

    suspend fun getPic() {
        var responseStr = AteroidObjectClass.ServerApi.retrofitService.getPictureOfDay(
            AteroidObjectClass.API_KEY
        )
        val pic = Moshi.Builder()
            .build()
            .adapter(PictureOfDayEntity::class.java)
            .fromJson(responseStr)
        if (pic != null) {
            Log.i("dbtest", pic.url)
            database.getAsteroidDaoPic().insert(pic)
        } else {
            Log.i("dbtest", "null")
        }
    }
}


fun List<Asteroid>.asAsteroidToEntities(): List<AsteroidEntity> {
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

fun List<AsteroidEntity>.asDomainModel(): List<Asteroid> {
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

