package com.udacity.asteroidradar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.udacity.asteroidradar.helper.PreferenceHelper
import com.udacity.asteroidradar.helper.Utility

class MainActivity : AppCompatActivity() {

    private lateinit var helper: PreferenceHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        helper = PreferenceHelper(this)

        if (helper.getLocal().equals("") || helper.getLocal() == null) {
            Utility.setLocale(this, "en")
        } else {
            if (helper.getLocal().equals("ar")) {
                Utility.setLocale(this, "ar")
            }
            if (helper.getLocal().equals("en")) {
                Utility.setLocale(this, "en")
            }

        }

    }

}
