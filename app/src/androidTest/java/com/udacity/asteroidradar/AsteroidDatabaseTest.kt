package com.udacity.asteroidradar

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.database.AsteroidDatabase
import junit.framework.Assert.assertEquals
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AsteroidDatabaseTest : TestCase() {


    private lateinit var dao: AsteroidDao
    private lateinit var db: AsteroidDatabase

    @Before
  public  override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        //  val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AsteroidDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .build()
        dao = db.getAsteroidDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


//    @Test
//    @Throws(Exception::class)
//    fun writeUserAndReadInList() = runBlocking {
//        val asteroid = AsteroidEntity()
//        dao.addAsteroid(asteroid)
//        val oneAsteroid = dao.getDiameterMax()
//        assertEquals(oneAsteroid?.estimated_diameter_max, 20.0)
//    }

}