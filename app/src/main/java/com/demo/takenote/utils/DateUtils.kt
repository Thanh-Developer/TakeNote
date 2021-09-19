package com.demo.takenote.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 *  Create by ThanhPQ
 */
object DateUtils {
    val getCurrentDateTime: Date
        get() = Calendar.getInstance().time

    fun getFormattedDateString(date: Date?): String? {
        try {
            var spf = SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy")
            val dateString = spf.format(date)
            val newDate = spf.parse(dateString)
            spf = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
            return spf.format(newDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }
}