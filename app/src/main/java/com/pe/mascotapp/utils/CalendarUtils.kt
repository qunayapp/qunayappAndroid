package com.pe.mascotapp.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class CalendarUtils {
    companion object {
        fun getTime(year: Int, month: Int, day: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            return calendar.time
        }

        fun getFormatDate(date: Date): String {
            val simpleDateFormat = SimpleDateFormat("E, dd 'de' MMM 'de' yyyy", Locale("es", "ES"))
            return simpleDateFormat.format(date)
        }

        fun getFormatDate2(date: Date): String {
            val sdf = SimpleDateFormat("dd 'de' MMM 'de' yyyy", Locale("es", "ES"))
            return sdf.format(date.time)
        }

        fun getFormatDate3(date: Date): String {
            val sdf = SimpleDateFormat("EEEE dd 'de' MMMM", Locale("es", "ES"))
            return sdf.format(date)
        }
    }
}