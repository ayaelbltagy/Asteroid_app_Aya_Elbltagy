package com.udacity.asteroidradar

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.udacity.asteroidradar.helper.PreferenceHelper
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var helper: PreferenceHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        helper = PreferenceHelper(this)

        if (helper.getLocal().equals("") || helper.getLocal() == null) {
            setLocale(this, "en")
        } else {
            if (helper.getLocal().equals("ar")) {
                setLocale(this, "ar")
            }
            if (helper.getLocal().equals("en")) {
                setLocale(this, "en")
            }

        }

    }

    fun setLocale(context: Context, languageCode: String) {
        helper.setLocal(languageCode)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResoureces(context, languageCode)
        }
        return updateResoureces(context, languageCode)

    }

    fun updateResoureces(context: Context, languageCode: String) {
        var local = Locale(languageCode.toLowerCase())
        Locale.setDefault(local)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = local
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

}
