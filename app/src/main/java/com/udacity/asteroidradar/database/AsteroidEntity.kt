package com.udacity.asteroidradar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asteroid_table")
data class AsteroidEntity (
    @PrimaryKey(autoGenerate = true)
    var id :Long = 0L,
    @ColumnInfo(name = "absolute_magnitude")
    var absolute_magnitude : Double =0.0,
    @ColumnInfo(name = "estimated_diameter_max")
    var estimated_diameter_max : Double =0.0
)