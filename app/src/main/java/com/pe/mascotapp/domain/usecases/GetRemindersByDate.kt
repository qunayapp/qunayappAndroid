package com.pe.mascotapp.domain.usecases

import android.util.Log
import com.pe.mascotapp.utils.CalendarUtils
import com.pe.mascotapp.utils.addDay
import com.pe.mascotapp.utils.dateToLocalDate
import com.pe.mascotapp.utils.inDates
import com.pe.mascotapp.vistas.adapters.ReminderPetsJoinEntity
import com.pe.mascotapp.vistas.adapters.TypeOption
import com.pe.mascotapp.vistas.adapters.ValueTextOption
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GetRemindersByDate @Inject constructor() {
    operator fun invoke(
        reminders: List<ReminderPetsJoinEntity>,
        filterDate: LocalDate
    ): List<ReminderPetsJoinEntity> {
        return reminders.filter { reminder ->
            val startEvent = CalendarUtils.joinDateAndHour(
                reminder.reminder.startDate,
                reminder.reminder.startHour
            )
            val startLocalDate = dateToLocalDate(startEvent)
            val dateTempReminder =
                when (reminder.reminder.repeatOption) {
                    ValueTextOption.DONT_REPEAT -> {
                        val dateTemp = CalendarUtils.joinDateAndHour(
                            reminder.reminder.startDate,
                            reminder.reminder.startHour
                        )
                        val formato = SimpleDateFormat("yyyyMMdd", Locale.getDefault())

                        val fechaStringHoy = formato.format(dateTemp)
                        val fechaString = filterDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                        if (fechaString == fechaStringHoy) true else null
                    }

                    ValueTextOption.ALL_DAYS -> {
                        // ver si hoy toca una alarma segun el intervalo de repeticion
                        val isAlarmToday = startEvent.let {
                            val diffInDays =
                                ChronoUnit.DAYS.between(dateToLocalDate(it), filterDate).toInt()
                            diffInDays % (reminder.reminder.countRepeatOption ?: 1) == 0
                        }

                        // obtener la fecha final de los recordatorios, si es nulo es para siempre quack
                        var endReminder: Date? = null
                        reminder.reminder.durationTypeRepeat?.let {
                            when (it) {
                                TypeOption.DATE -> reminder.reminder.durationRepeat?.let { date ->
                                    endReminder =
                                        CalendarUtils.stringToDate(date, "dd 'de' MMM 'de' yyyy")
                                }

                                TypeOption.COUNTER -> reminder.reminder.durationRepeat?.let { counter ->
                                    val totalDays =
                                        (counter.toInt() * (reminder.reminder.countRepeatOption
                                            ?: 1)).toString()
                                    endReminder = startEvent.addDay(totalDays.toInt())
                                }

                                else -> {
                                    endReminder = null
                                }
                            }
                        }
                        Log.e("isAlarmToday", isAlarmToday.toString())
                        Log.e("startReminder", startEvent.toString())
                        if (isAlarmToday && filterDate.inDates(
                                dateToLocalDate(startEvent),
                                endReminder?.let { dateToLocalDate(it) }
                            )
                        ) {
                            Log.d(
                                "MyWorker",
                                "all_days " + startEvent.hours.toString() + " : " + startEvent.minutes.toString()
                            )
                            true
                        } else {
                            null
                        }
                    }

                    ValueTextOption.ALL_WEEKS -> {
                        val isAlarmToday = startEvent.let {
                            val diffInDays =
                                ChronoUnit.DAYS.between(dateToLocalDate(it), filterDate).toInt()
                            (diffInDays % ((reminder.reminder.countRepeatOption ?: 1) * 7)) == 0
                        }

                        var endReminder: Date? = null

                        reminder.reminder.durationTypeRepeat?.let {
                            when (it) {
                                TypeOption.DATE -> reminder.reminder.durationRepeat?.let { date ->
                                    endReminder =
                                        CalendarUtils.stringToDate(date, "dd 'de' MMM 'de' yyyy")
                                }

                                TypeOption.COUNTER -> reminder.reminder.durationRepeat?.let { counter ->
                                    val totalDays =
                                        (counter.toInt() * (reminder.reminder.countRepeatOption
                                            ?: 1) * 7)
                                    endReminder = startEvent.addDay(totalDays)
                                }

                                else -> {
                                    endReminder = null
                                }
                            }
                        }

                        Log.e("week isAlarmToday", isAlarmToday.toString())
                        Log.e("week startReminder", startEvent.toString())
                        if (filterDate.inDates(dateToLocalDate(startEvent),
                                endReminder?.let { dateToLocalDate(it) }) && isAlarmToday
                        ) {
                            Log.d(
                                "MyWorker",
                                "all_weeks " + startEvent.hours.toString() + " : " + startEvent.minutes.toString()
                            )
                            true
                        } else {
                            null
                        }
                    }

                    ValueTextOption.ALL_MONTHS -> {
                        val isAlarmToday = startEvent.let {
                            val isToday =
                                ChronoUnit.DAYS.between(startLocalDate, filterDate).toInt() == 0
                            val isThisMonth = ChronoUnit.MONTHS.between(
                                startLocalDate.withDayOfMonth(1),
                                filterDate.withDayOfMonth(1)
                            ).toInt() % (reminder.reminder.countRepeatOption ?: 1) == 0
                            val lastDayOfMonth = filterDate.lengthOfMonth()
                            val effectiveDay =
                                if (startLocalDate.dayOfMonth > lastDayOfMonth) lastDayOfMonth else startLocalDate.dayOfMonth

                            val isThisDay = effectiveDay == filterDate.dayOfMonth
                            (isToday || (isThisMonth && isThisDay))
                        }

                        var endReminder: LocalDate? = null
                        reminder.reminder.durationTypeRepeat?.let {
                            when (it) {
                                TypeOption.DATE -> reminder.reminder.durationRepeat?.let { dateStr ->
                                    val formatter = DateTimeFormatter.ofPattern(
                                        "dd 'de' MMM 'de' yyyy",
                                        Locale("es", "ES")
                                    )
                                    endReminder = LocalDate.parse(dateStr, formatter)
                                }

                                TypeOption.COUNTER -> reminder.reminder.durationRepeat?.let { counter ->
                                    endReminder = startLocalDate.plusMonths(
                                        counter.toLong() * (reminder.reminder.countRepeatOption
                                            ?: 1)
                                    )
                                }

                                else -> {
                                    endReminder = null
                                }
                            }
                        }
                        if (filterDate.inDates(startLocalDate, endReminder) && isAlarmToday) {
                            Log.d(
                                "MyWorker",
                                "all_weeks " + startEvent.hours.toString() + " : " + startEvent.minutes.toString()
                            )
                            true
                        } else {
                            null
                        }
                    }

                    ValueTextOption.ALL_YEARS -> {
                        // ver si hoy toca el evento
                        val isAlarmToday = startLocalDate.let {
                            val isToday =
                                ChronoUnit.DAYS.between(startLocalDate, filterDate).toInt() == 0
                            val isThisYear = ChronoUnit.YEARS.between(
                                startLocalDate.withDayOfYear(1),
                                filterDate.withDayOfYear(1)
                            ).toInt() % (reminder.reminder.countRepeatOption ?: 1) == 0
                            val isThisMonth = startLocalDate.month == filterDate.month
                            val lastDayOfMonth = filterDate.lengthOfMonth()
                            val effectiveDay =
                                if (startLocalDate.dayOfMonth > lastDayOfMonth) lastDayOfMonth else startLocalDate.dayOfMonth

                            val isThisDay = effectiveDay == filterDate.dayOfMonth
                            (isToday || (isThisYear && isThisMonth && isThisDay))
                        }

                        var endReminder: LocalDate? = null
                        reminder.reminder.durationTypeRepeat?.let {
                            when (it) {
                                TypeOption.DATE -> reminder.reminder.durationRepeat?.let { dateStr ->
                                    val formatter = DateTimeFormatter.ofPattern(
                                        "dd 'de' MMM 'de' yyyy",
                                        Locale("es", "ES")
                                    )
                                    endReminder = LocalDate.parse(dateStr, formatter)
                                }

                                TypeOption.COUNTER -> reminder.reminder.durationRepeat?.let { counter ->
                                    endReminder = startLocalDate.plusYears(
                                        counter.toLong() * (reminder.reminder.countRepeatOption
                                            ?: 1)
                                    )
                                }

                                else -> {
                                    endReminder = null
                                }
                            }
                        }
                        if (filterDate.inDates(startLocalDate, endReminder) && isAlarmToday) {
                            Log.d(
                                "MyWorker",
                                "all_weeks " + startEvent.hours.toString() + " : " + startEvent.minutes.toString()
                            )
                            true
                        } else {
                            null
                        }
                    }

                    else -> null
                }
            dateTempReminder == true
        }.sortedBy { CalendarUtils.parseDate("${it.reminder.startHour} ${it.reminder.startDate}") }
    }
}