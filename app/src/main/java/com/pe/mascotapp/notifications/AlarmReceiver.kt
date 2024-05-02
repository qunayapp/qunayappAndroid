package com.pe.mascotapp.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Parcelable
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.pe.mascotapp.R
import com.pe.mascotapp.vistas.ReminderActivity
import kotlinx.parcelize.Parcelize
import java.util.Date

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val NOTIFICATION_ID = 1
    }

    override fun onReceive(
        context: Context,
        p1: Intent?,
    ) {
        p1?.parcelable<AlarmNotificationData>("BUNDLE_DATE")?.let {
            createSimpleNotification(context, it)
        }
    }

    private fun createSimpleNotification(
        context: Context,
        notification1: AlarmNotificationData,
    ) {
        val intent =
            Intent(context, ReminderActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        val flag = PendingIntent.FLAG_IMMUTABLE
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, notification1.reminderId, intent, flag)

        val notification =
            NotificationCompat.Builder(context, ReminderActivity.MY_CHANNEL_ID)
                .setContentTitle(notification1.title)
                .setContentText(notification1.title + " \n" + notification1.description)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(notification1.title + " \n" + notification1.description),
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

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? =
    when {
        Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else ->
            @Suppress("DEPRECATION")
            getParcelableExtra(key)
                as? T
    }

@Parcelize
class AlarmNotificationData(
    val reminderId: Int,
    val title: String,
    val description: String,
    val alarm: Date,
) : Parcelable
