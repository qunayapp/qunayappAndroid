package com.pe.mascotapp.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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


        fun convertirFechaATime(fechaString: String): Date? {
            val formato = SimpleDateFormat("dd 'de' MMM 'de' yyyy", Locale("es", "ES"))
            return try {
                formato.parse(fechaString)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        fun formatMonthYear(localDate: LocalDate, locale: Locale): String {
            val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", locale)
            return localDate.format(formatter)
        }

        fun getAbbreviatedDayName(localDate: LocalDate, locale: Locale): String {
            val formatter = DateTimeFormatter.ofPattern("EEE", locale)
            return localDate.format(formatter)
        }



        fun fechaCumplidaHoy(fecha: Date): Boolean {
            val formato = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
            val fechaHoy = Calendar.getInstance().time // Obtener la fecha de hoy

            val fechaStringHoy = formato.format(fechaHoy)
            val fechaString = formato.format(fecha)

            return fechaString == fechaStringHoy
        }

        fun joinDateAndHour(dateString: String, hourString: String): Date {
            try {
                val dateFormat = SimpleDateFormat("dd 'de' MMM 'de' yyyy", Locale("es", "ES"))
                val hourFormat = SimpleDateFormat("HH:mm", Locale("es", "ES"))

                val date = dateFormat.parse(dateString)
                val hour = hourFormat.parse(hourString)

                val dateCalendar = Calendar.getInstance()
                if (date != null) {
                    dateCalendar.time = date
                }

                val horaCalendar = Calendar.getInstance()
                if (hour != null) {
                    horaCalendar.time = hour
                }

                dateCalendar.set(Calendar.HOUR_OF_DAY, horaCalendar.get(Calendar.HOUR_OF_DAY))
                dateCalendar.set(Calendar.MINUTE, horaCalendar.get(Calendar.MINUTE))

                return dateCalendar.time
            } catch (e: Exception) {
                return Calendar.getInstance().time
            }
        }

        fun daysInWeekArray(selectedDate: LocalDate): ArrayList<LocalDate> {
            val days = ArrayList<LocalDate>()
            var current: LocalDate? = sundayForDate(selectedDate)
            val endDate = current!!.plusWeeks(1)
            while (current!!.isBefore(endDate)) {
                days.add(current)
                current = current.plusDays(1)
            }
            return days
        }

        private fun sundayForDate(current: LocalDate): LocalDate? {
            var current = current
            val oneWeekAgo = current.minusWeeks(1)
            while (current.isAfter(oneWeekAgo)) {
                if (current.dayOfWeek == DayOfWeek.SUNDAY) return current
                current = current.minusDays(1)
            }
            return null
        }

        fun convertLocalDateToDate(localDate: LocalDate): Date {
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        }
    }

}

fun Date.addDay(days: Int): Date? {

    val calendar = Calendar.getInstance()
    calendar.time = this
    return try {
        calendar.add(Calendar.DAY_OF_YEAR, days) // Suma un día a la fecha
        calendar.time
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Date.establecerHoraEnFechaActual(horaMinutos: String): Date? {
    val horaMinutosPartes = horaMinutos.trim().replace(" ", "").split(":")
    if (horaMinutosPartes.size != 2) {
        // Formato de hora incorrecto
        return null
    }
    Log.e("quack", horaMinutosPartes.toString())
    Log.e("quack", horaMinutosPartes[0].toIntOrNull().toString())
    Log.e("quack", horaMinutosPartes[1].toIntOrNull().toString())


    val hora = horaMinutosPartes[0].toIntOrNull() ?: return null
    val minutos = horaMinutosPartes[1].toIntOrNull() ?: return null

    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, hora)
    calendar.set(Calendar.MINUTE, minutos)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    return calendar.time
}

fun Date.addMinutes(minutes: Int): Date? {

    val calendar = Calendar.getInstance()
    calendar.time = this
    return try {
        calendar.add(Calendar.MINUTE, minutes) // Suma un día a la fecha
        calendar.time
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Date.addHours(hours: Int): Date? {

    val calendar = Calendar.getInstance()
    calendar.time = this
    return try {
        calendar.add(Calendar.HOUR_OF_DAY, hours) // Suma un día a la fecha
        calendar.time
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}