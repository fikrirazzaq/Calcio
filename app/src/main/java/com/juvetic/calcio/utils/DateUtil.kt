package com.juvetic.calcio.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {
        fun formatDate(d: String?) : String {
            val format = SimpleDateFormat("YYYY-MM-DD", Locale.getDefault())
            val date: Date = format.parse(d)
            format.applyPattern("MMM dd")
            return format.format(date)
        }

        fun formatDateWithYear(d: String?) : String {
            val format = SimpleDateFormat("YYYY-MM-DD", Locale.getDefault())
            val date: Date = format.parse(d)
            format.applyPattern("MMM dd, YYYY")
            return format.format(date)
        }
    }
}