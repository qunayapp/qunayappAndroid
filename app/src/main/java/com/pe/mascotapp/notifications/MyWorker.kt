package com.pe.mascotapp.notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pe.mascotapp.domain.models.ReminderWithPets
import com.pe.mascotapp.domain.usecases.GetRemindersWithPetsUseCase
import com.pe.mascotapp.utils.CalendarUtils
import com.pe.mascotapp.utils.addDay
import com.pe.mascotapp.utils.addHours
import com.pe.mascotapp.utils.addMinutes
import com.pe.mascotapp.utils.dateToLocalDate
import com.pe.mascotapp.utils.establecerHoraEnFechaActual
import com.pe.mascotapp.utils.inDates
import com.pe.mascotapp.vistas.ReminderActivity
import com.pe.mascotapp.vistas.adapters.TypeOption
import com.pe.mascotapp.vistas.adapters.ValueTextOption
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit
import kotlin.math.abs

@HiltWorker
class MyWorker
@AssistedInject
constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val getRemindersWithPetsUseCase: GetRemindersWithPetsUseCase,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result =
        coroutineScope {
            try {
                Log.d("MyWorker", "Run work manager")
                val alarmHelper = AlarmEventHelper(applicationContext)
                alarmHelper.createChannel()
                getRemindersWithPetsUseCase.invoke().onEach { reminders ->
                    alarmHelper.setAlarmPeriod(reminders)
                }
                return@coroutineScope Result.success()
            } catch (e: Exception) {
                Log.d("MyWorker", "exception in doWork ${e.message}")
                return@coroutineScope Result.failure()
            }
        }
}

class AlarmEventHelper(private val applicationContext: Context) {
    fun createChannel() {
        val channel =
            NotificationChannel(
                ReminderActivity.MY_CHANNEL_ID,
                ReminderActivity.MY_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT,
            )

        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    fun setAlarmPeriod(reminders: List<ReminderWithPets>) {
        reminders.forEach { reminder ->
            Log.d("MyWorker", "reminder id " + reminder.reminder.reminderId)
            if (!reminder.reminder.isActivated) return@forEach
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
                        Log.d(
                            "MyWorker",
                            "dont repeat " + dateAlarm?.hours.toString() + " : " + dateAlarm?.minutes.toString()
                        )
                        if (dateAlarm != null && CalendarUtils.fechaCumplidaHoy(dateTemp)) dateAlarm else null
                    }

                    ValueTextOption.ALL_DAYS -> {
                        val today = LocalDate.now()
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
                            val diffInDays =
                                ChronoUnit.DAYS.between(dateToLocalDate(it), today).toInt()
                            Log.e("diffInDays", diffInDays.toString())
                            Log.e("count repeat", reminder.reminder.countRepeatOption.toString())
                            diffInDays % (reminder.reminder.countRepeatOption ?: 1) == 0
                        } ?: false

                        // obtener la fecha final de los recordatorios, si es nulo es para siempre quack
                        var endReminder: Date? = null
                        reminder.reminder.durationTypeRepeat?.let {
                            when (it) {
                                TypeOption.DATE -> reminder.reminder.durationRepeat?.let { date ->
                                    endReminder =
                                        CalendarUtils.stringToDate(date, "dd 'de' MMM 'de' yyyy")
                                    Log.e("quack", endReminder.toString())
                                }

                                TypeOption.COUNTER -> reminder.reminder.durationRepeat?.let { counter ->
                                    val totalDays =
                                        (counter.toInt() * (reminder.reminder.countRepeatOption
                                            ?: 1)).toString()
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
                        } else {
                            null
                        }
                    }

                    ValueTextOption.ALL_WEEKS -> {
                        val today = LocalDate.now()
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
                            val diffInDays =
                                ChronoUnit.DAYS.between(dateToLocalDate(it), today).toInt()
                            Log.e("week diffInDays", diffInDays.toString())
                            Log.e(
                                "week count repeat",
                                reminder.reminder.countRepeatOption.toString()
                            )
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
                                    val totalDays =
                                        (counter.toInt() * (reminder.reminder.countRepeatOption
                                            ?: 1) * 7)
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
                        } else {
                            null
                        }
                    }

                    ValueTextOption.ALL_MONTHS -> {
                        val today = LocalDate.now()
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
                            val diffInDays =
                                ChronoUnit.DAYS.between(dateToLocalDate(it), today).toInt()
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
                                    val totalDays =
                                        (counter.toInt() * (reminder.reminder.countRepeatOption
                                            ?: 1) * 30)

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
                        } else {
                            null
                        }
                    }

                    ValueTextOption.ALL_YEARS -> {
                        val today = LocalDate.now()
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
                            val diffInDays =
                                ChronoUnit.DAYS.between(dateToLocalDate(it), today).toInt()
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
                        } else {
                            null
                        }
                    }

                    else -> null
                }
            Log.d("MyWorker", "fecha es diferente de null:" + (dateTempReminder != null).toString())

            dateTempReminder?.let {
                Log.d(
                    "MyWorker",
                    " reminder hora y minutos : " + it.hours.toString() + " : " + it.minutes.toString()
                )
                Log.d("MyWorker", " evento hora y minutos : " + reminder.reminder.startHour)
                var dateEvent = it.addMinutes(reminder.reminder.alarmInMinutes)
                dateEvent = dateEvent?.addHours(reminder.reminder.alarmInHours)
                dateEvent = dateEvent?.addMinutes(reminder.reminder.alarmInDays)
                val event: Date = dateEvent ?: Calendar.getInstance().time
                val title = "Recuerda que tienes un evento el ${
                    CalendarUtils.toSimpleString(
                        event,
                    )
                } a la hora ${reminder.reminder.startHour} - ${reminder.reminder.title} ${
                    reminder.pets.joinToString(
                        ",",
                    ) { it.name }
                }"

                val description = reminder.reminder.description
                scheduleNotification(
                    reminder.reminder.reminderId?.toInt() ?: 0,
                    title,
                    description,
                    it
                )
            }
        }
    }

    private fun scheduleNotification(
        id: Int,
        title: String,
        description: String,
        alarm: Date,
    ) {
        val intent = Intent(applicationContext, AlarmReceiver::class.java)
        intent.putExtra("BUNDLE_DATE", AlarmNotificationData(id, title, description, alarm))
        val pendingIntent =
            PendingIntent.getBroadcast(
                applicationContext,
                id,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )

        val alarmManager =
            applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.time, pendingIntent)
    }
}
