package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.models.ResponseModel
import com.udacity.asteroidradar.models.moResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "wat0gv7aur4kc7FwltauMiuEFl8K9hlOHdQ0JXKL"
private const val BASE_URL ="https://api.nasa.gov/"
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

interface AsteroidApiService {
    @GET("neo/rest/v1/feed?")
    suspend fun getList(
        @Query("start_date") START_DATE: String,
        @Query("end_date") END_DATE: String,
        @Query("api_key") API_KEY: String): List<moResponse>

    @GET("planetary/apod")
    suspend fun getPictureOfDay(@Query("api_key") API_KEY: String) : PictureOfDay

   
}

object ServerApi {
    val retrofitService: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }


}