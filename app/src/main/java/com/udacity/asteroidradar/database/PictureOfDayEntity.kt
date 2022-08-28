package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "pic_table")
data class PictureOfDayEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @Json(name = "media_type") val mediaType: String,
    val title: String,
    val url: String
)