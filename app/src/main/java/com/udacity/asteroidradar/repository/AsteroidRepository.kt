package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.map
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AteroidObjectClass
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.database.PictureOfDayEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Array.get

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
         map(database.getAsteroidDao().getAsteroid()) {
            it.asDomainModel()
        }

    val todayAsteroidList: LiveData<List<Asteroid>> =
      map(database.getAsteroidDao().getTodayAsteroid(Constants.getCurrentDate())){
            it.asDomainModel()
        }

    val pictureOfDay: LiveData<PictureOfDay> =
        map(database.getAsteroidDaoPic().get()){
            PictureOfDay(
                mediaType = it.mediaType,
                title =  it.title,
                url =  it.url
            )
        }


    suspend fun refreshedAsteroid() {
        withContext(Dispatchers.IO) {
            val asteroids = AteroidObjectClass.getAsteroids()
            database.getAsteroidDao().addAsteroid(asteroids.asAsteroidToEntities())
        }
    }

    suspend fun getPictureOfDay(): PictureOfDay {
        lateinit var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureOfDay = AteroidObjectClass.getPictureOfDay()
            database.getAsteroidDaoPic().insert(pictureOfDay)
        }
        return pictureOfDay
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

