package com.pe.mascotapp.extentions

import android.graphics.PorterDuff
import android.os.Build
import android.view.View
import android.widget.TimePicker

fun View.changeTintColor(color: Int) {
    this.background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
}

fun TimePicker.getTime(): String {
    val hour: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.hour
    } else {
        this.currentHour
    }

    val minute: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.minute
    } else {
        this.currentMinute
    }
    return "$hour : $minute"
}