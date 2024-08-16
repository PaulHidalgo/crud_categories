package com.phidalgo.crudcategories.util

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object DataUtil {
    fun longToDateTime(timeInMillis: Long): String {
        val date = Date(timeInMillis)
        val format = SimpleDateFormat("EEEE, dd MMM HH:mm", Locale.getDefault())
        return format.format(date)
    }
}