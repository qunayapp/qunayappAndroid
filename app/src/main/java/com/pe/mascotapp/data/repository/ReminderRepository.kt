package com.pe.mascotapp.data.repository

import com.pe.mascotapp.domain.models.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    fun getReminders(pageNumber: Int): Flow<List<Reminder>>
    suspend fun insertReminder(reminder: Reminder):Long
    suspend fun updateReminder(reminder: Reminder)
}