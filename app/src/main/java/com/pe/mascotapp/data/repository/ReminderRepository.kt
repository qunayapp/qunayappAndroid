package com.pe.mascotapp.data.repository

import com.pe.mascotapp.domain.models.Reminder
import com.pe.mascotapp.domain.models.ReminderWithPets
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    fun getReminders(pageNumber: Int): Flow<List<Reminder>>
    fun insertReminder(reminder: Reminder)
}