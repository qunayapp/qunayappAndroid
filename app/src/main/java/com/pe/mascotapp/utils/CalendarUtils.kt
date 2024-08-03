package com.pe.mascotapp.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarUtils {
    companion object {
        const val CONST_FORMAT = "dd/MM/yyyy"
        const val SECONDARY_FORMAT = "d 'de' MMM 'de' yyyy"

        fun getTime(
            year: Int,
            month: Int,
            day: Int,
        ): Date {
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

        fun getFormatDate4(date: Date): String {
            val sdf = SimpleDateFormat(CONST_FORMAT, Locale("es", "ES"))
            return sdf.format(date.time)
        }

        fun parseDate(dateString: String): Date {
            val format = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale("es", "ES"))
            return format.parse(dateString) ?: Date()
        }

        fun stringToDate(
            dateString: String,
            format: String,
        ): Date? {
            val simpleFormat = SimpleDateFormat(format, Locale("es", "ES"))
            return try {
                simpleFormat.parse(dateString)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        fun toSimpleString(date: Date): String {
            val format = SimpleDateFormat("dd/MM/yyy")
            return format.format(date)
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

        fun formatMonthYear(
            localDate: LocalDate,
            locale: Locale,
        ): String {
            val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", locale)
            return localDate.format(formatter)
        }

        fun getAbbreviatedDayName(
            localDate: LocalDate,
            locale: Locale,
        ): String {
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

        fun joinDateAndHour(
            dateString: String,
            hourString: String,
        ): Date {
            try {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES"))
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
                e.printStackTrace()
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

        fun convertLocalDateToCalendar(localDate: LocalDate): Calendar {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, localDate.year)
            calendar.set(
                Calendar.MONTH,
                localDate.monthValue - 1
            ) // Note: Calendar.MONTH is zero-based
            calendar.set(Calendar.DAY_OF_MONTH, localDate.dayOfMonth)
            return calendar
        }

        fun convertStringFormatToLocalDate(
            dateString: String,
            format: String,
        ): LocalDate? {
            if (dateString.isBlank()) {
                return null
            }
            val dateFormatter = DateTimeFormatter.ofPattern(format, Locale("es", "ES"))
            return try {
                LocalDate.parse(dateString, dateFormatter)
            } catch (e: Exception) {
                null
            }
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

fun LocalDate.inDates(
    startDate: LocalDate?,
    endDate: LocalDate?,
): Boolean {
    if (startDate == null) {
        Log.e("Date", "fecha de inicio es null")
        return false
    }
    if (endDate == null){
        return true
    }
    Log.e("quack",(this.isAfter(startDate) || this.isEqual(startDate)).toString())
    Log.e("quack",(this.isBefore(endDate) || this.isEqual(endDate)).toString())

    return (this.isAfter(startDate) || this.isEqual(startDate)) &&
            (this.isBefore(endDate) || this.isEqual(endDate))
}

fun Date.inDates(
    startDate: Date?,
    endDate: Date?,
): Boolean {
    if (startDate == null) {
        Log.e("Date", "fecha de inicio es null")
        return false
    }
    val isBetweenYear = this.year >= startDate.year && this.year <= (endDate?.year ?: this.year)
    if (!isBetweenYear) {
        return false
    }
    if (this.year == startDate.year && this.month < startDate.month) {
        return false
    }
    if (this.year == (endDate?.year ?: this.year) && this.month > (endDate?.month ?: this.month)) {
        return false
    }
    if (this.year == startDate.year && this.month == startDate.month && this.date < startDate.date) {
        return false
    }
    if (this.year == (endDate?.year ?: this.year) &&
        this.month == (endDate?.month ?: this.month) &&
        this.date > (endDate?.date ?: this.date)
    ) {
        return false
    }
    return true
}

fun Date.addMonth(months: Int): Date? {
    return try {
        val calendar = Calendar.getInstance()
        calendar.time = this
        val originalDay = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.add(Calendar.MONTH, months)
        // Verificar si la fecha resultante está en el siguiente mes
        // y si el día original era el último día del mes
        val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        if (originalDay == calendar.getActualMinimum(Calendar.DAY_OF_MONTH) &&
            calendar.get(Calendar.DAY_OF_MONTH) > lastDayOfMonth
        ) {
            // Ajustar la fecha para que sea el último día del mes siguiente
            calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth)
        }
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

fun dateToLocalDate(date: Date): LocalDate {
    // Convertir Date a Instant
    val instant = date.toInstant()
    // Convertir Instant a LocalDate usando la zona horaria del sistema
    return instant.atZone(ZoneId.systemDefault()).toLocalDate()
}
private fun localDateToDate(localDate: LocalDate): Date {
    // Convertir LocalDate a ZonedDateTime
    val zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault())
    // Convertir ZonedDateTime a Instant
    val instant = zonedDateTime.toInstant()
    // Convertir Instant a Date
    return Date.from(instant)
}
fun LocalDate.establecerHoraEnFechaActual(horaMinutos: String): Date? {
    val horaMinutosPartes = horaMinutos.trim().replace(" ", "").split(":")
    if (horaMinutosPartes.size != 2) {
        // Formato de hora incorrecto
        return null
    }
    val hora = horaMinutosPartes[0].toIntOrNull() ?: return null
    val minutos = horaMinutosPartes[1].toIntOrNull() ?: return null

    val calendar = Calendar.getInstance()
    calendar.time = localDateToDate(this)
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


fun Date.getDayOfMonth(): Int {
    val localDate: LocalDate = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    return localDate.dayOfMonth
}

fun Date.getDayOfWeek(): DayOfWeek {
    val localDate: LocalDate = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    return localDate.dayOfWeek
}

fun Date.getMonthYear(): Month {
    val localDate: LocalDate = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    return localDate.month
}
