package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAsteroid(asteroid : List<AsteroidEntity>)

    @Query("SELECT * FROM asteroid_table ORDER by closeApproachDate")
    fun getAsteroid () : LiveData<List<AsteroidEntity>>


    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate=:date ORDER BY closeApproachDate ASC ")
    fun getTodayAsteroid(date: String): LiveData<List<AsteroidEntity>>


}
