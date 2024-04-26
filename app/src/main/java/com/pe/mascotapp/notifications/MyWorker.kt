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
import com.pe.mascotapp.utils.addHours
import com.pe.mascotapp.utils.addMinutes
import com.pe.mascotapp.utils.establecerHoraEnFechaActual
import com.pe.mascotapp.utils.getDayOfMonth
import com.pe.mascotapp.utils.getDayOfWeek
import com.pe.mascotapp.utils.getMonthYear
import com.pe.mascotapp.vistas.ReminderActivity
import com.pe.mascotapp.vistas.adapters.ValueTextOption
import com.pe.mascotapp.vistas.adapters.mapValueTextOption
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Calendar
import java.util.Date

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
                    }.launchIn(this)
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

            reminder.reminder.alarm.let { alarm ->
                Log.d("MyWorker", reminder.reminder.repeatOption.toString())

                val dateTempReminder =
                    when (reminder.reminder.repeatOption) {
                        ValueTextOption.DONT_REPEAT -> {
                            val dateTemp = CalendarUtils.joinDateAndHour(reminder.reminder.startDate, reminder.reminder.startHour)
                            val dateAlarm = getDateAlarm(reminder.reminder.alarmOption, dateTemp, -alarm)
                            if (dateAlarm != null && CalendarUtils.fechaCumplidaHoy(dateTemp)) dateTemp else null
                        }

                        ValueTextOption.ALL_DAYS -> {
                            val dateTemp = CalendarUtils.joinDateAndHour(reminder.reminder.startDate, reminder.reminder.startHour)
                            val dateAlarm = getDateAlarm(reminder.reminder.alarmOption, dateTemp, -alarm)
                            if (dateAlarm?.after(Calendar.getInstance().time) == true && !reminder.reminder.alarmsPassed()) {
                                Calendar.getInstance().time.establecerHoraEnFechaActual("${dateAlarm.hours}: ${dateAlarm.minutes}")
                            } else {
                                null
                            }
                        }

                        ValueTextOption.ALL_WEEKS -> {
                            val dateTemp = CalendarUtils.joinDateAndHour(reminder.reminder.startDate, reminder.reminder.startHour)
                            val dateAlarm = getDateAlarm(reminder.reminder.alarmOption, dateTemp, -alarm)
                            if (dateAlarm?.after(Calendar.getInstance().time) == true && !reminder.reminder.alarmsPassed()) {
                                val dayOfWeekAlarm = dateTemp.getDayOfWeek()
                                val dayOfWeekToday = Calendar.getInstance().time.getDayOfWeek()
                                if (dayOfWeekAlarm == dayOfWeekToday) {
                                    Calendar.getInstance().time.establecerHoraEnFechaActual("${dateAlarm.hours}:${dateAlarm.minutes}")
                                } else {
                                    null
                                }
                            } else {
                                null
                            }
                        }

                        ValueTextOption.ALL_MONTHS -> {
                            val dateTemp = CalendarUtils.joinDateAndHour(reminder.reminder.startDate, reminder.reminder.startHour)
                            val dateAlarm = getDateAlarm(reminder.reminder.alarmOption, dateTemp, -alarm)
                            if (dateAlarm?.after(Calendar.getInstance().time) == true && !reminder.reminder.alarmsPassed()) {
                                val dayOfAlarm = dateTemp.getDayOfMonth()
                                val dayOfToday = Calendar.getInstance().time.getDayOfMonth()
                                if (dayOfAlarm == dayOfToday) {
                                    Calendar.getInstance().time.establecerHoraEnFechaActual("${dateAlarm.hours}:${dateAlarm.minutes}")
                                } else {
                                    null
                                }
                            } else {
                                null
                            }
                        }

                        ValueTextOption.ALL_YEARS -> {
                            val dateTemp = CalendarUtils.joinDateAndHour(reminder.reminder.startDate, reminder.reminder.startHour)
                            val dateAlarm = getDateAlarm(reminder.reminder.alarmOption, dateTemp, -alarm)
                            if (dateAlarm?.after(Calendar.getInstance().time) == true && !reminder.reminder.alarmsPassed()) {
                                val today = Calendar.getInstance().time
                                val dayOfAlarm = dateAlarm.getDayOfMonth()
                                val monthOfAlarm = dateAlarm.getMonthYear()
                                val dayOfToday = today.getDayOfMonth()
                                val monthOfToday = today.getMonthYear()
                                if (dayOfAlarm == dayOfToday && monthOfAlarm == monthOfToday) {
                                    Calendar.getInstance().time.establecerHoraEnFechaActual("${dateAlarm.hours}:${dateAlarm.minutes}")
                                } else {
                                    null
                                }
                            } else {
                                null
                            }
                        }

                        else -> null
                    }
                Log.d("MyWorker", "fecha es null:" + (dateTempReminder != null).toString())

                dateTempReminder?.let {
                    Log.d("MyWorker", alarm.toString())
                    val title =
                        "Recuerda que en $alarm ${reminder.reminder.alarmOption.mapValueTextOption()} , a las ${reminder.reminder.startHour}, tienes " + reminder.reminder.title + " de " +
                            reminder.pets.joinToString(",") { it.name }
                    val description = reminder.reminder.description
                    scheduleNotification(reminder.reminder.reminderId?.toInt() ?: 0, title, description, it)
                }
            }
        }
    }

    private fun getDateAlarm(
        alarmOption: ValueTextOption,
        dateTempReminder: Date,
        alarm: Int,
    ): Date? {
        return when (alarmOption) {
            ValueTextOption.MINUTES -> {
                dateTempReminder.addMinutes(-alarm)
            }

            ValueTextOption.HOUR -> {
                dateTempReminder.addHours(-alarm)
            }

            ValueTextOption.DAYS -> {
                dateTempReminder.addHours(-alarm)
            }

            else -> dateTempReminder.addMinutes(-alarm)
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

        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.time, pendingIntent)
    }
}
