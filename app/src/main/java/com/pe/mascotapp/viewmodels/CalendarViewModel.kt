package com.pe.mascotapp.viewmodels

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pe.mascotapp.domain.usecases.GetRemindersWithPetsUseCase
import com.pe.mascotapp.utils.CalendarUtils
import com.pe.mascotapp.utils.addDay
import com.pe.mascotapp.utils.addHours
import com.pe.mascotapp.utils.addMinutes
import com.pe.mascotapp.utils.establecerHoraEnFechaActual
import com.pe.mascotapp.utils.inDates
import com.pe.mascotapp.vistas.adapters.ReminderPetsJoinEntity
import com.pe.mascotapp.vistas.adapters.TypeOption
import com.pe.mascotapp.vistas.adapters.ValueTextOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getRemindersWithPetsUseCase: GetRemindersWithPetsUseCase,
) : ViewModel() {
    var originalReminders = listOf<ReminderPetsJoinEntity>()
    private var getRemindersJob: Job? = null
    private val _listReminders = MutableLiveData<List<ReminderPetsJoinEntity>>()
    val listReminders: LiveData<List<ReminderPetsJoinEntity>> = _listReminders
    private val _listFilteredReminders = MutableLiveData<List<ReminderPetsJoinEntity>>()
    val listFilteredReminders: LiveData<List<ReminderPetsJoinEntity>> = _listFilteredReminders
    val remindersIsEmpty: ObservableBoolean = ObservableBoolean(true)
    fun getReminders(pageNumber: Int, filterDate: LocalDate) {
        if (pageNumber == 0) originalReminders = listOf()
        getRemindersJob?.cancel()
        getRemindersJob = getRemindersWithPetsUseCase(pageNumber)
            .onEach { reminders ->
                if (pageNumber != 0 && reminders.isEmpty()) return@onEach
                originalReminders = reminders.map {
                    ReminderPetsJoinEntity(it)
                }
                remindersIsEmpty.set(originalReminders.isEmpty())
                _listReminders.postValue(originalReminders)
                getFilterReminders(filterDate)
            }
            .launchIn(viewModelScope)
    }

    fun getFilterReminders(filterDate: LocalDate) {
        val filteredReminders = filterReminders(originalReminders, filterDate)
            .sortedBy { CalendarUtils.parseDate("${it.reminder.startHour} ${it.reminder.startDate}") }
        _listFilteredReminders.postValue(filteredReminders)

    }

    private fun filterReminders(
        reminders: List<ReminderPetsJoinEntity>,
        filterDate: LocalDate
    ): List<ReminderPetsJoinEntity> {
        return reminders.filter { reminder ->

            val dateTempReminder =
                when (reminder.reminder.repeatOption) {
                    ValueTextOption.DONT_REPEAT -> {
                        val dateTemp = CalendarUtils.joinDateAndHour(
                            reminder.reminder.startDate,
                            reminder.reminder.startHour
                        )
                        var dateAlarm = dateTemp.addMinutes(-reminder.reminder.alarmInMinutes)
                        dateAlarm = dateAlarm?.addHours(-reminder.reminder.alarmInHours)
                        dateAlarm = dateAlarm?.addDay(-reminder.reminder.alarmInDays)
                        val formato = SimpleDateFormat("yyyyMMdd", Locale.getDefault())

                        val fechaStringHoy = formato.format(dateTemp)
                        val fechaString = filterDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                        if (dateAlarm != null && fechaString == fechaStringHoy) true else null
                    }

                    ValueTextOption.ALL_DAYS -> {
                        val today = filterDate
                        val startEvent = CalendarUtils.joinDateAndHour(
                            reminder.reminder.startDate,
                            reminder.reminder.startHour
                        )
                        //fecha de inicio del recordatorio
                        var startReminder = startEvent.addMinutes(-reminder.reminder.alarmInMinutes)
                        startReminder = startReminder?.addHours(-reminder.reminder.alarmInHours)
                        startReminder = startReminder?.addDay(-reminder.reminder.alarmInDays)


                        // ver si hoy toca una alarma segun el intervalo de repeticion
                        val isAlarmToday = startReminder?.let {
                            val diffInDays = ChronoUnit.DAYS.between(dateToLocalDate(it), today).toInt()
                            diffInDays % (reminder.reminder.countRepeatOption ?: 1) == 0
                        } ?: false

                        // obtener la fecha final de los recordatorios, si es nulo es para siempre quack
                        var endReminder: Date? = null
                        reminder.reminder.durationTypeRepeat?.let {
                            when (it) {
                                TypeOption.DATE -> reminder.reminder.durationRepeat?.let { date ->
                                    endReminder =
                                        CalendarUtils.stringToDate(date, "dd 'de' MMM 'de' yyyy")
                                }

                                TypeOption.COUNTER -> reminder.reminder.durationRepeat?.let { counter ->
                                    val totalDays = (counter.toInt() * (reminder.reminder.countRepeatOption?:1)).toString()
                                    endReminder = startReminder?.addDay(totalDays.toInt())
                                }

                                else -> {
                                    endReminder = null
                                }
                            }
                        }
                        Log.e("isAlarmToday", isAlarmToday.toString())
                        Log.e("startReminder", startReminder.toString())
                        if (startReminder != null && isAlarmToday && today.inDates(
                                dateToLocalDate(startReminder),
                                endReminder?.let { dateToLocalDate(it) }
                            )
                        ) {
                            Log.d(
                                "MyWorker",
                                "all_days " + startReminder.hours.toString() + " : " + startReminder.minutes.toString()
                            )
                            today.establecerHoraEnFechaActual("${startReminder.hours}:${startReminder.minutes}")
                            true
                        } else {
                            null
                        }
                    }

                    ValueTextOption.ALL_WEEKS -> {
                        val today = filterDate
                        val startEvent = CalendarUtils.joinDateAndHour(
                            reminder.reminder.startDate,
                            reminder.reminder.startHour
                        )

                        //fecha de inicio del recordatorio
                        var startReminder = startEvent.addMinutes(-reminder.reminder.alarmInMinutes)
                        startReminder = startReminder?.addHours(-reminder.reminder.alarmInHours)
                        startReminder = startReminder?.addDay(-reminder.reminder.alarmInDays)

                        // ver si hoy toca una alarma segun el intervalo de repeticion
                        val isAlarmToday = startReminder?.let {
                            val diffInDays = ChronoUnit.DAYS.between(dateToLocalDate(it), today).toInt()
                            (diffInDays % ((reminder.reminder.countRepeatOption ?: 1) * 7)) == 0
                        } ?: false

                        var endReminder: Date? = null

                        reminder.reminder.durationTypeRepeat?.let {
                            when (it) {
                                TypeOption.DATE -> reminder.reminder.durationRepeat?.let { date ->
                                    endReminder =
                                        CalendarUtils.stringToDate(date, "dd 'de' MMM 'de' yyyy")
                                }

                                TypeOption.COUNTER -> reminder.reminder.durationRepeat?.let { counter ->
                                    val totalDays = (counter.toInt() * (reminder.reminder.countRepeatOption?:1) * 7)
                                    endReminder = startReminder?.addDay(totalDays)
                                }

                                else -> {
                                    endReminder = null
                                }
                            }
                        }

                        Log.e("week isAlarmToday", isAlarmToday.toString())
                        Log.e("week startReminder", startReminder.toString())
                        if (startReminder != null &&
                            today.inDates(dateToLocalDate(startReminder),
                                endReminder?.let { dateToLocalDate(it) }) &&
                            isAlarmToday
                        ) {
                            Log.d(
                                "MyWorker",
                                "all_weeks " + startReminder.hours.toString() + " : " + startReminder.minutes.toString()
                            )
                            today.establecerHoraEnFechaActual("${startReminder.hours}:${startReminder.minutes}")
                            true
                        } else {
                            null
                        }
                    }

                    ValueTextOption.ALL_MONTHS -> {
                        val today = filterDate
                        val startEvent = CalendarUtils.joinDateAndHour(
                            reminder.reminder.startDate,
                            reminder.reminder.startHour
                        )

                        //fecha de inicio recordatorio
                        var startReminder = startEvent.addMinutes(-reminder.reminder.alarmInMinutes)
                        startReminder = startReminder?.addHours(-reminder.reminder.alarmInHours)
                        startReminder = startReminder?.addDay(-reminder.reminder.alarmInDays)

                        // ver si hoy toca una alarma segun el intervalo de repeticion
                        val isAlarmToday = startReminder?.let {
                            val diffInDays = ChronoUnit.DAYS.between(dateToLocalDate(it), today).toInt()
                            (diffInDays % ((reminder.reminder.countRepeatOption ?: 1) * 30)) == 0
                        } ?: false


                        var endReminder: Date? = null
                        reminder.reminder.durationTypeRepeat?.let {
                            when (it) {
                                TypeOption.DATE -> reminder.reminder.durationRepeat?.let { date ->
                                    endReminder =
                                        CalendarUtils.stringToDate(date, "dd 'de' MMM 'de' yyyy")
                                }

                                TypeOption.COUNTER -> reminder.reminder.durationRepeat?.let { counter ->
                                    val totalDays = (counter.toInt() * (reminder.reminder.countRepeatOption?:1) * 30)

                                    endReminder = startReminder?.addDay(totalDays)
                                }

                                else -> {
                                    endReminder = null
                                }
                            }
                        }
                        if (startReminder != null &&
                            today.inDates(dateToLocalDate(startReminder),
                                endReminder?.let { dateToLocalDate(it) })
                            && isAlarmToday
                        ) {
                            Log.d(
                                "MyWorker",
                                "all_weeks " + startReminder.hours.toString() + " : " + startReminder.minutes.toString()
                            )
                            today.establecerHoraEnFechaActual("${startReminder.hours}:${startReminder.minutes}")
                            true
                        } else {
                            null
                        }
                    }

                    ValueTextOption.ALL_YEARS -> {
                        val today = filterDate
                        val startEvent = CalendarUtils.joinDateAndHour(
                            reminder.reminder.startDate,
                            reminder.reminder.startHour
                        )

                        //fecha de inicio recordatorio
                        var startReminder = startEvent.addMinutes(-reminder.reminder.alarmInMinutes)
                        startReminder = startReminder?.addHours(-reminder.reminder.alarmInHours)
                        startReminder = startReminder?.addDay(-reminder.reminder.alarmInDays)

                        // ver si hoy toca una alarma segun el intervalo de repeticion
                        val isAlarmToday = startReminder?.let {
                            val diffInDays = ChronoUnit.DAYS.between(dateToLocalDate(it), today).toInt()
                            (diffInDays % ((reminder.reminder.countRepeatOption ?: 1) * 365)) == 0
                        } ?: false

                        var endReminder: Date? = null
                        reminder.reminder.durationTypeRepeat?.let {
                            when (it) {
                                TypeOption.DATE -> reminder.reminder.durationRepeat?.let { date ->
                                    endReminder =
                                        CalendarUtils.stringToDate(date, "dd 'de' MMM 'de' yyyy")
                                }

                                TypeOption.COUNTER -> reminder.reminder.durationRepeat?.let { counter ->
                                    endReminder = startReminder?.addDay(counter.toInt() * 365)
                                }

                                else -> {
                                    endReminder = null
                                }
                            }
                        }
                        if (startReminder != null &&
                            today.inDates(dateToLocalDate(startReminder),
                                endReminder?.let { dateToLocalDate(it) })
                            && isAlarmToday
                        ) {
                            Log.d(
                                "MyWorker",
                                "all_weeks " + startReminder.hours.toString() + " : " + startReminder.minutes.toString()
                            )
                            today.establecerHoraEnFechaActual("${startReminder.hours}:${startReminder.minutes}")
                            true
                        } else {
                            null
                        }
                    }

                    else -> null
                }
            Log.d("MyWorker", "fecha es diferente de null:" + (dateTempReminder != null).toString())
            dateTempReminder == true
        }
    }

    fun dateToLocalDate(date: Date): LocalDate {
        // Convertir Date a Instant
        val instant = date.toInstant()
        // Convertir Instant a LocalDate usando la zona horaria del sistema
        return instant.atZone(ZoneId.systemDefault()).toLocalDate()
    }
}
