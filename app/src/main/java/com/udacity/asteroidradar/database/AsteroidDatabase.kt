package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATA_BASE_NAME = "AsteroidDB"

@Database(entities = [AsteroidEntity::class],version =1,exportSchema = false)
abstract class AsteroidDatabase :RoomDatabase() {

    // to access database you should get from DAO
    abstract fun getAsteroidDao(): AsteroidDao

    // to prevent more connection should use one instance from database so make single tone design pattern
    companion object {

        @Volatile
        private var INSTANCE: AsteroidDatabase? = null

        // function to get object from database class
        fun getInstance(context: Context): AsteroidDatabase {

            // to block code until this instance created
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        DATA_BASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}