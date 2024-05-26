package com.pe.mascotapp.domain.usecases

import com.pe.mascotapp.data.repository.ReminderPetJoinRepository
import com.pe.mascotapp.domain.models.ReminderWithPets
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRemindersWithPetsUseCase
    @Inject
    constructor(
        private val reminderPetJoinRepository: ReminderPetJoinRepository,
    ) {
        operator fun invoke(pageNumber: Int? = null): Flow<List<ReminderWithPets>> {
            pageNumber?.let {
                return reminderPetJoinRepository.getReminderPet()
            }
            return reminderPetJoinRepository.getAllReminders()
        }
    }

class DeleteReminderWithPets
    @Inject
    constructor(
        private val reminderPetJoinRepository: ReminderPetJoinRepository,
    ) {
        operator fun invoke(reminderId: Long) {
            reminderPetJoinRepository.deleteReminder(reminderId)
        }
    }
