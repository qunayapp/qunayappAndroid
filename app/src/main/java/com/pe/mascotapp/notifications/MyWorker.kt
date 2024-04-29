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
import com.pe.mascotapp.utils.addMonth
import com.pe.mascotapp.utils.establecerHoraEnFechaActual
import com.pe.mascotapp.utils.getDayOfMonth
import com.pe.mascotapp.utils.getDayOfWeek
import com.pe.mascotapp.utils.getMonthYear
import com.pe.mascotapp.utils.inDates
import com.pe.mascotapp.vistas.ReminderActivity
import com.pe.mascotapp.vistas.adapters.ValueTextOption
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope
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

            reminder.reminder.alarm.let { alarm ->
                Log.d("MyWorker", "repeat Option: " + reminder.reminder.repeatOption.toString())

                val dateTempReminder =
                    when (reminder.reminder.repeatOption) {
                        ValueTextOption.DONT_REPEAT -> {
                            val dateTemp = CalendarUtils.joinDateAndHour(reminder.reminder.startDate, reminder.reminder.startHour)
                            val dateAlarm = getDateAlarm(reminder.reminder.alarmOption, dateTemp, -alarm)
                            Log.d("MyWorker", "dont repeat " + dateAlarm?.hours.toString() + " : " + dateAlarm?.minutes.toString())
                            if (dateAlarm != null && CalendarUtils.fechaCumplidaHoy(dateTemp)) dateAlarm else null
                        }

                        ValueTextOption.ALL_DAYS -> {
                            val today = Calendar.getInstance().time
                            val startEvent = CalendarUtils.joinDateAndHour(reminder.reminder.startDate, reminder.reminder.startHour)
                            val startReminder = getDateAlarm(reminder.reminder.alarmOption, startEvent, -alarm)
                            var endReminder: Date? = null
                            reminder.reminder.countRepeatOption?.let {
                                endReminder = startReminder?.addDay(it)
                            }
                            if (startReminder != null && today.inDates(startReminder, endReminder)) {
                                Log.d("MyWorker", "all_days " + startReminder.hours.toString() + " : " + startReminder.minutes.toString())
                                today.establecerHoraEnFechaActual("${startReminder.hours}:${startReminder.minutes}")
                            } else {
                                null
                            }
                        }

                        ValueTextOption.ALL_WEEKS -> {
                            val today = Calendar.getInstance().time
                            val startEvent = CalendarUtils.joinDateAndHour(reminder.reminder.startDate, reminder.reminder.startHour)
                            val startReminder = getDateAlarm(reminder.reminder.alarmOption, startEvent, -alarm)
                            var endReminder: Date? = null
                            reminder.reminder.countRepeatOption?.let {
                                endReminder = startReminder?.addDay(7 * it)
                            }
                            val dayOfWeekAlarm = startEvent.getDayOfWeek()
                            val dayOfWeekToday = today.getDayOfWeek()
                            if (startReminder != null && today.inDates(startReminder, endReminder) && dayOfWeekAlarm == dayOfWeekToday) {
                                Log.d("MyWorker", "all_weeks " + startReminder.hours.toString() + " : " + startReminder.minutes.toString())
                                today.establecerHoraEnFechaActual("${startReminder.hours}:${startReminder.minutes}")
                            } else {
                                null
                            }
                        }

                        ValueTextOption.ALL_MONTHS -> {
                            val today = Calendar.getInstance().time
                            val startEvent = CalendarUtils.joinDateAndHour(reminder.reminder.startDate, reminder.reminder.startHour)
                            val startReminder = getDateAlarm(reminder.reminder.alarmOption, startEvent, -alarm)
                            var endReminder: Date? = null
                            reminder.reminder.countRepeatOption?.let {
                                endReminder = startReminder?.addMonth(it)
                            }
                            val dayOfAlarm = startEvent.getDayOfMonth()
                            val dayOfToday = today.getDayOfMonth()
                            if (startReminder != null && today.inDates(startReminder, endReminder) && dayOfAlarm == dayOfToday) {
                                Log.d("MyWorker", "all_weeks " + startReminder.hours.toString() + " : " + startReminder.minutes.toString())
                                today.establecerHoraEnFechaActual("${startReminder.hours}:${startReminder.minutes}")
                            } else {
                                null
                            }
                        }

                        ValueTextOption.ALL_YEARS -> {
                            val today = Calendar.getInstance().time
                            val startEvent = CalendarUtils.joinDateAndHour(reminder.reminder.startDate, reminder.reminder.startHour)
                            val startReminder = getDateAlarm(reminder.reminder.alarmOption, startEvent, -alarm)
                            var endReminder: Date? = null
                            reminder.reminder.countRepeatOption?.let {
                                endReminder = startReminder?.addMonth(it * 12)
                            }
                            val dayOfAlarm = startEvent.getDayOfMonth()
                            val monthOfAlarm = startEvent.getMonthYear()
                            val dayOfToday = today.getDayOfMonth()
                            val monthOfToday = today.getMonthYear()
                            if (startReminder != null &&
                                today.inDates(
                                    startReminder,
                                    endReminder,
                                ) && dayOfAlarm == dayOfToday && monthOfAlarm == monthOfToday
                            ) {
                                Log.d("MyWorker", "all_weeks " + startReminder.hours.toString() + " : " + startReminder.minutes.toString())
                                today.establecerHoraEnFechaActual("${startReminder.hours}:${startReminder.minutes}")
                            } else {
                                null
                            }
                        }

                        else -> null
                    }
                Log.d("MyWorker", "fecha es diferente de null:" + (dateTempReminder != null).toString())

                dateTempReminder?.let {
                    Log.d("MyWorker", " alarma : " + alarm.toString())
                    Log.d("MyWorker", " reminder hora y minutos : " + it.hours.toString() + " : " + it.minutes.toString())
                    Log.d("MyWorker", " evento hora y minutos : " + reminder.reminder.startHour)
                    val title =
                        "Recuerda que tienes un recordatorio a las " +
                            reminder.reminder.startHour + " " + reminder.reminder.title + " " +
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
                dateTempReminder.addMinutes(alarm)
            }

            ValueTextOption.HOUR -> {
                dateTempReminder.addHours(alarm)
            }

            ValueTextOption.DAYS -> {
                dateTempReminder.addHours(alarm)
            }

            else -> dateTempReminder.addMinutes(alarm)
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
