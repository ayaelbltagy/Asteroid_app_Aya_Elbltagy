package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.models.AsteroidListModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val START_DATE = "2015-09-07"
private const val END_DATE = "2015-09-08"
private const val API_KEY = "wat0gv7aur4kc7FwltauMiuEFl8K9hlOHdQ0JXKL/"
private const val BASE_URL =
    "https://api.nasa.gov/neo/rest/v1/feed?start_date=" + START_DATE + "&end_date=" + END_DATE + "&api_key=" + API_KEY

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ServerApiService {
    @GET("realestate")
    fun getProperties(): Call<List<AsteroidListModel>>
}

object ServerApi {
    val retrofitService: ServerApiService by lazy {
        retrofit.create(ServerApiService::class.java)
    }
}