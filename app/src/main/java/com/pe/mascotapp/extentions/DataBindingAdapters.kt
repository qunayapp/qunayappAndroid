package com.pe.mascotapp.extentions

import android.graphics.ColorMatrixColorFilter
import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.google.android.material.imageview.ShapeableImageView
import com.pe.mascotapp.R

@BindingAdapter("android:setSrc")
fun setImageDrawable(view: ImageView, drawable: Int) {
    view.setImageResource(drawable)
}

@BindingAdapter("android:setStateCategory")
fun setStateCategory(view: ImageView, isSelected: Boolean) {
    if (isSelected) {
        view.setBackgroundResource(R.drawable.ic_category_selected)
        view.drawable.setColorFilter(
            ContextCompat.getColor(view.context, R.color.white),
            PorterDuff.Mode.SRC_ATOP
        )
        return
    }
    view.setBackgroundResource(R.drawable.ic_category_normal)
    view.drawable.setColorFilter(
        ContextCompat.getColor(view.context, R.color.secondary),
        PorterDuff.Mode.SRC_ATOP
    )
}

@BindingAdapter("android:setStatePet")
fun setStatePet(view: ShapeableImageView, isSelected: Boolean) {
    if (isSelected) {
        setUnlocked(view)
        view.strokeWidth = 8.00f
        view.strokeColor =
            ContextCompat.getColorStateList(view.context, R.color.green200)
        return
    }
    view.strokeWidth = 0.00f
    view.strokeColor = null
    setLocked(view)
    view.setBackgroundResource(R.drawable.ic_category_normal)
}

fun setLocked(v: ImageView) {
    val cf = ColorMatrixColorFilter(
        floatArrayOf(
            0.33f, 0.33f, 0.33f, 0f, 0f, // Red
            0.33f, 0.33f, 0.33f, 0f, 0f, // Green
            0.33f, 0.33f, 0.33f, 0f, 0f, // Blue
            0f, 0f, 0f, 1f, 0f // Alpha
        )
    )
    v.colorFilter = cf
    v.imageAlpha = 255 // 128 = 0.5
}

fun setUnlocked(v: ImageView) {
    v.colorFilter = null
    v.imageAlpha = 255
}

@BindingConversion
fun booleanToVisibility(value: Boolean?) = if (value == true) View.VISIBLE else View.GONE