package com.pe.mascotapp.domain.usecases

import com.pe.mascotapp.data.repository.ReminderRepository
import com.pe.mascotapp.domain.models.Reminder
import javax.inject.Inject

class InsertReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(reminder: Reminder): Long {
        return reminderRepository.insertReminder(reminder)
    }
}