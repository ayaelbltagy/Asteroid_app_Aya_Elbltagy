package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import retrofit2.HttpException

class RefreshDataWorker (appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshAsteroidsWorker"
    }


    override suspend fun doWork(): Result {
          val database = AsteroidDatabase.getInstance(applicationContext)
           val repo = AsteroidRepository(database)
        return try {
            repo.refreshedAsteroid()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
    }