package com.pe.mascotapp.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import com.pe.mascotapp.vistas.HomeActivity

class AlarmReceiver : BroadcastReceiver()  {

    override fun onReceive(context: Context, intent: Intent) {

        val service1 = Intent(context, HomeActivity::class.java)
        service1.data = Uri.parse("custom://" + System.currentTimeMillis())
        ContextCompat.startForegroundService(context, service1)
        Log.d("WALKIRIA", " ALARM RECEIVED!!!")
    }
}