package com.pe.mascotapp.domain.repository

import com.pe.mascotapp.data.data_source.ReminderDao
import com.pe.mascotapp.data.repository.ReminderRepository
import com.pe.mascotapp.domain.models.Reminder
import kotlinx.coroutines.flow.Flow

class ReminderRepositoryImpl(
    private val reminderDao: ReminderDao
) : ReminderRepository {
    override fun getReminders(pageNumber: Int): Flow<List<Reminder>> {
        return reminderDao.getReminders(pageNumber)
    }

    override suspend fun insertReminder(reminder: Reminder):Long {
        return reminderDao.insertReminder(reminder)
    }

    override suspend fun updateReminder(reminder: Reminder) {
        return reminderDao.updateReminder(reminder)
    }
}