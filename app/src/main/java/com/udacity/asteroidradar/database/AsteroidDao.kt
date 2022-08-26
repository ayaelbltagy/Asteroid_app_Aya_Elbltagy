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

    /**
     * Selects and returns all rows in the table,
     *
     */    @Query("SELECT * FROM asteroid_table")
    fun getAsteroid () : LiveData<List<AsteroidEntity>>

    /**
     * Selects and returns the Asteroid with given nightId.
     */
    @Query("SELECT * from asteroid_table WHERE id = :key")
    fun getAsteroidWithId(key: Long): LiveData<AsteroidEntity>



 }