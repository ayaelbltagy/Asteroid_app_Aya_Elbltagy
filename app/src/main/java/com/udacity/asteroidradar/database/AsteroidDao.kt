package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface AsteroidDao {

    // method for a add data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAsteroid(asteroid : AsteroidEntity)

    // query fro get data
    @Query("SELECT * FROM asteroid_table")
    fun getAsteroid () : LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroid_table LIMIT 1")
    suspend fun getDiameterMax(): AsteroidEntity?


 }