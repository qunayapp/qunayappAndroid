package com.pe.mascotapp.domain.usecases

import com.pe.mascotapp.data.repository.ReminderPetJoinRepository
import javax.inject.Inject

class GetRemindersWithPetsUseCase @Inject constructor(
    private val reminderPetJoinRepository: ReminderPetJoinRepository
) {
    operator fun invoke(pageNumber: Int) {
        reminderPetJoinRepository.getReminders(pageNumber)
    }
}