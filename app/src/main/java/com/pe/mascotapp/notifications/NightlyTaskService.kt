package com.pe.mascotapp.notifications

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.pe.mascotapp.domain.usecases.GetRemindersWithPetsUseCase
import javax.inject.Inject

class NightlyTaskService @Inject constructor(
    val getRemindersWithPetsUseCase: GetRemindersWithPetsUseCase
) : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}