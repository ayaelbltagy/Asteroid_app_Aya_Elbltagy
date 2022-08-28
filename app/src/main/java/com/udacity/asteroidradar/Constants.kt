package com.udacity.asteroidradar

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7

    fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        return formatter.format(date)
    }

    fun getDate7Days(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_WEEK, 7)
        val newDate = calendar.time
        val y: String = formatter.format(newDate)
        return y
    }


}