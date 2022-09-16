package com.udacity.asteroidradar.api

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.AteroidObjectClass.ServerApi.retrofitService
import com.udacity.asteroidradar.database.PictureOfDayEntity
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object AteroidObjectClass {

    const val API_KEY = "wat0gv7aur4kc7FwltauMiuEFl8K9hlOHdQ0JXKL"
    private const val BASE_URL = "https://api.nasa.gov/"

    // moshi is a lib used to convert json
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

    object ServerApi {
        val retrofitService: AsteroidApiService by lazy {
            retrofit.create(AsteroidApiService::class.java)
        }
    }

    suspend fun getAsteroids() : List<Asteroid> {
        val responseStr = retrofitService.getAsteroids("","", API_KEY)
        val responseJsonObject = JSONObject(responseStr)
        return parseAsteroidsJsonResult(responseJsonObject)
    }

    suspend fun getPictureOfDay() {
       var responseStr =  retrofitService.getPictureOfDay(API_KEY)
        val pic = Moshi.Builder()
            .build()
            .adapter(PictureOfDayEntity::class.java)
            .fromJson(responseStr)


             }


}