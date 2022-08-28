package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.PictureOfDay

@Dao
interface PictureDao {
    @Query("SELECT * FROM pic_table")
    fun get(): LiveData<PictureOfDayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pic: PictureOfDayEntity): Long
}