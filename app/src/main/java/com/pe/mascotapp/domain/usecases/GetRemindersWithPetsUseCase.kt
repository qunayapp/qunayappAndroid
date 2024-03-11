package com.pe.mascotapp.domain.usecases

import com.pe.mascotapp.data.repository.ReminderPetJoinRepository
import com.pe.mascotapp.domain.models.Reminder
import com.pe.mascotapp.domain.models.ReminderPetJoin
import com.pe.mascotapp.domain.models.ReminderWithPets
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRemindersWithPetsUseCase @Inject constructor(
    private val reminderPetJoinRepository: ReminderPetJoinRepository
) {
    operator fun invoke(pageNumber: Int): Flow<List<ReminderWithPets>> {
        return reminderPetJoinRepository.getReminderPet(pageNumber)
    }
}

class GetRemindersPetsJoinUseCase @Inject constructor(
    private val reminderPetJoinRepository: ReminderPetJoinRepository
) {
    operator fun invoke(pageNumber: Int): Flow<List<ReminderPetJoin>> {
        return reminderPetJoinRepository.getReminders(pageNumber)
    }
}