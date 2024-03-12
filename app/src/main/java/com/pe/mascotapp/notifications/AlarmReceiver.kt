package com.pe.mascotapp.notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Parcelable
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.pe.mascotapp.R
import com.pe.mascotapp.domain.usecases.GetRemindersWithPetsUseCase
import com.pe.mascotapp.utils.CalendarUtils
import com.pe.mascotapp.utils.addDay
import com.pe.mascotapp.utils.addHours
import com.pe.mascotapp.utils.addMinutes
import com.pe.mascotapp.utils.establecerHoraEnFechaActual
import com.pe.mascotapp.vistas.ReminderActivity
import com.pe.mascotapp.vistas.adapters.ValueTextOption
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.parcelize.Parcelize
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID = 1
    }

    override fun onReceive(context: Context, p1: Intent?) {
        p1?.parcelable<AlarmNotificationData>("BUNDLE_DATE")?.let {
            createSimpleNotification(context, it)
        }
    }

    private fun createSimpleNotification(context: Context, notification1: AlarmNotificationData) {
        val intent = Intent(context, ReminderActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val flag = PendingIntent.FLAG_IMMUTABLE
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, notification1.reminderId, intent, flag)

        val notification = NotificationCompat.Builder(context, ReminderActivity.MY_CHANNEL_ID)
            .setContentTitle(notification1.title)
            .setContentText(notification1.description)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(notification1.description)
            )
            .setColor(ContextCompat.getColor(context, R.color.blue))
            .setSmallIcon(R.drawable.mascotapp_icon)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notification1.reminderId, notification)
    }

}

@HiltWorker
class MyWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val getRemindersWithPetsUseCase: GetRemindersWithPetsUseCase
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            try {
                Log.d("MyWorker", "Run work manager")
                //Do Your task here
                doYourTask()
                createChannel()
                coroutineScope {
                    var notificationId = 0
                    getRemindersWithPetsUseCase.invoke(1).onEach { reminders ->
                        reminders.forEach { reminder ->
                            Log.d("MyWorker", reminder.reminder.isActivated.toString())
                            if (!reminder.reminder.isActivated) return@forEach
                            if (reminder.reminder.isAllDay && CalendarUtils.convertirFechaATime(reminder.reminder.startDate)?.let { it.addDay(-1)?.let { CalendarUtils.fechaCumplidaHoy(it) } } == true) {
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
                        }
                    }.launchIn(this)
                }

                Result.success()
            } catch (e: Exception) {
                Log.d("MyWorker", "exception in doWork ${e.message}")
                Result.failure()
            }
        } catch (e: Exception) {
            Log.d("MyWorker", "exception in doWork ${e.message}")
            Result.failure()
        }
    }

    private fun doYourTask() {
        Log.d("quack", "asdfasdf11348127348")
        //]getRemindersWithPetsUseCase.invoke(1).collectIndexed { index, value ->
        //    Log.e("quack", "asdfasdf")
        //}
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


object WorkManagerScheduler {
    fun createWorker(
        context: Context
    ) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<MyWorker>()
            .setConstraints(constraints)
            .setInitialDelay(6, TimeUnit.HOURS) // Ejecutar a las 6 de la mañana
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }

    fun scheduleWorker(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            MyWorker::class.java,
            15,
            TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "MyWorker",
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicWorkRequest
        )
    }
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

@Parcelize
class AlarmNotificationData(
    val reminderId: Int,
    val title: String,
    val description: String,
    val alarm: Date
) : Parcelable
