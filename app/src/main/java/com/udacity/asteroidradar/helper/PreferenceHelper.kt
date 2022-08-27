package com.udacity.asteroidradar.helper

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import java.lang.NullPointerException
import java.util.*

public class PreferenceHelper {
    private lateinit var app_prefs : SharedPreferences
    private val local = "Local"

    constructor(context: Context?){
        try {
            if (context != null) {
                app_prefs = context.getSharedPreferences("shoeApp", Context.MODE_PRIVATE)
            }
        } catch (e: NullPointerException) {
        }
    }

    fun getLocal(): String? {
        return app_prefs!!.getString(local, null)
    }

    fun setLocal(API_Local: String?) {
        val edit = app_prefs!!.edit()
        edit.putString(local, API_Local)
        edit.apply()
    }


}