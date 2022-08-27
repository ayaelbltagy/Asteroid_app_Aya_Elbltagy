package com.udacity.asteroidradar.helper

import android.content.Context
import android.os.Build
import java.util.*

class Utility {

    companion object   {
        private lateinit var helper :PreferenceHelper
        fun setLocale(context: Context, languageCode: String) {
            helper = PreferenceHelper(context)
            helper.setLocal(languageCode)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return updateResoureces(context, languageCode)
            }
            return updateResoureces(context, languageCode)

        }
        fun updateResoureces(context: Context, languageCode: String) {
            var local =  Locale(languageCode.toLowerCase())
            Locale.setDefault(local)
            val resources = context.resources
            val configuration = resources.configuration
            configuration.locale = local
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
    }




}