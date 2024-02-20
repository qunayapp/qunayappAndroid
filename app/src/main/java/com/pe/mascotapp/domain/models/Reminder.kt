package com.pe.mascotapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val reminderId: Int? = null,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val imageReminder: Int,
    val isActivated: Boolean
)
