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
import com.pe.mascotapp.domain.usecases.GetRemindersWithPetsUseCase
import com.pe.mascotapp.utils.CalendarUtils
import com.pe.mascotapp.utils.addDay
import com.pe.mascotapp.utils.addHours
import com.pe.mascotapp.utils.addMinutes
import com.pe.mascotapp.utils.establecerHoraEnFechaActual
import com.pe.mascotapp.utils.getDayOfMonth
import com.pe.mascotapp.utils.getDayOfWeek
import com.pe.mascotapp.utils.getMonthYear
import com.pe.mascotapp.vistas.ReminderActivity
import com.pe.mascotapp.vistas.adapters.ValueTextOption
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Calendar
import java.util.Date


@HiltWorker
class MyWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val getRemindersWithPetsUseCase: GetRemindersWithPetsUseCase
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            Log.d("MyWorker", "Run work manager")
            createChannel()
            var notificationId = 0
            getRemindersWithPetsUseCase.invoke().onEach { reminders ->
                reminders.forEach { reminder ->
                    Log.d("MyWorker", reminder.reminder.isActivated.toString())
                    if (!reminder.reminder.isActivated) return@forEach

                    val dateTimeStartDate = CalendarUtils.convertirFechaATime(reminder.reminder.startDate)

                    val isEventTomorrow = dateTimeStartDate?.addDay(-1)?.let { CalendarUtils.fechaCumplidaHoy(it) }

                    if (reminder.reminder.isAllDay && isEventTomorrow == true) {
                        CalendarUtils.convertirFechaATime(reminder.reminder.startDate)?.let {
                            val title = "Mañana -" + reminder.reminder.startDate + " " + reminder.reminder.title
                            val description = reminder.reminder.description
                            notificationId += 1
                            scheduleNotification(notificationId, title, description, it)
                        }
                        return@forEach
                    }

                    reminder.reminder.alarms.forEach { alarm ->
                        Log.d("MyWorker", reminder.reminder.repeatOption.toString())

                        val date1 = when (reminder.reminder.repeatOption) {
                            ValueTextOption.DONT_REPEAT -> {
                                val dateTemp = CalendarUtils.joinDateAndHour(reminder.reminder.startDate, reminder.reminder.startHour)
                                if (CalendarUtils.fechaCumplidaHoy(dateTemp)) dateTemp else null
                            }

                            ValueTextOption.ALL_DAYS -> Calendar.getInstance().time.establecerHoraEnFechaActual(reminder.reminder.startHour)

                            ValueTextOption.MONDAY_FRIDAY -> {
                                val calendar = Calendar.getInstance()
                                val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]
                                val isWeekday = dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.FRIDAY
                                if (isWeekday) {
                                    Calendar.getInstance().time.establecerHoraEnFechaActual(reminder.reminder.startHour)
                                } else null
                            }

                            ValueTextOption.ALL_WEEKS -> {
                                val dayOfWeekAlarm = CalendarUtils.convertirFechaATime(reminder.reminder.startDate)?.getDayOfWeek()
                                val dayOfWeekToday = Calendar.getInstance().time.getDayOfWeek()
                                if (dayOfWeekAlarm == dayOfWeekToday) {
                                    Calendar.getInstance().time.establecerHoraEnFechaActual(reminder.reminder.startHour)
                                } else null
                            }

                            ValueTextOption.ALL_MONTHS -> {
                                val dayOfAlarm = CalendarUtils.convertirFechaATime(reminder.reminder.startDate)?.getDayOfMonth()
                                val dayOfToday = Calendar.getInstance().time.getDayOfMonth()
                                if (dayOfAlarm == dayOfToday) {
                                    Calendar.getInstance().time.establecerHoraEnFechaActual(reminder.reminder.startHour)
                                } else null
                            }

                            ValueTextOption.ALL_YEARS -> {
                                val dateAlarm = CalendarUtils.convertirFechaATime(reminder.reminder.startDate)
                                val today = Calendar.getInstance().time
                                val dayOfAlarm = dateAlarm?.getDayOfMonth()
                                val monthOfAlarm = dateAlarm?.getMonthYear()
                                val dayOfToday = today.getDayOfMonth()
                                val monthOfToday = today.getMonthYear()
                                if (dayOfAlarm == dayOfToday && monthOfAlarm == monthOfToday) {
                                    Calendar.getInstance().time.establecerHoraEnFechaActual(reminder.reminder.startHour)
                                } else null
                            }

                            else -> null
                        }
                        Log.d("MyWorker", "fecha es null:" + (date1 != null).toString())

                        date1?.let {
                            Log.d("MyWorker", alarm)

                            val date = when (alarm) {
                                ValueTextOption.MINUTES_15.name -> {
                                    date1.addMinutes(-15)
                                }

                                ValueTextOption.MINUTES_30.name -> {
                                    date1.addMinutes(-30)
                                }

                                ValueTextOption.MINUTES_HOUR.name -> {
                                    date1.addHours(-1)
                                }

                                else -> null
                            }
                            date?.let {
                                Log.d("notificaction", it.hours.toString())
                                val title = "Hoy " + reminder.reminder.startHour + " " + reminder.reminder.title
                                val description = reminder.reminder.description
                                notificationId += 1
                                scheduleNotification(notificationId, title, description, it)
                            }
                        }
                    }

                    reminder.reminder.dateAlarms.forEach { dateAlarms ->
                        val dateTemp = CalendarUtils.joinDateAndHour(reminder.reminder.startDate, reminder.reminder.startHour)
                        if (CalendarUtils.fechaCumplidaHoy(dateTemp)) {
                            Log.d("notificaction", dateTemp.hours.toString())
                            val title = "Hoy " + reminder.reminder.startHour + " " + reminder.reminder.title
                            val description = reminder.reminder.description
                            notificationId += 1
                            scheduleNotification(notificationId, title, description, dateTemp)
                        }
                    }
                }
            }.launchIn(this)
            return@coroutineScope Result.success()
        } catch (e: Exception) {
            Log.d("MyWorker", "exception in doWork ${e.message}")
            return@coroutineScope Result.failure()
        }
    }


    private fun scheduleNotification(id: Int, title: String, description: String, alarm: Date) {
        val intent = Intent(applicationContext, AlarmReceiver::class.java)
        intent.putExtra("BUNDLE_DATE", AlarmNotificationData(id, title, description, alarm))
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            id,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.time, pendingIntent)
    }


    private fun createChannel() {
        val channel = NotificationChannel(
            ReminderActivity.MY_CHANNEL_ID,
            ReminderActivity.MY_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

}