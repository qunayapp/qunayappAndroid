package com.pe.mascotapp.extentions

import android.graphics.PorterDuff
import android.view.View

fun View.changeTintColor(color: Int) {
    this.background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
}