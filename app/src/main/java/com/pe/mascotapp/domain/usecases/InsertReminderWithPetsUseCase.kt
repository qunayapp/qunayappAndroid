package com.pe.mascotapp.domain.usecases

import com.pe.mascotapp.data.repository.ReminderPetJoinRepository
import com.pe.mascotapp.domain.models.ReminderPetJoin
import javax.inject.Inject

class InsertReminderWithPetsUseCase @Inject constructor(
    private val reminderPetJoinRepository: ReminderPetJoinRepository
) {
    operator fun invoke(reminderWithPets: ReminderPetJoin) {
        reminderPetJoinRepository.insertReminderPet(reminderWithPets)
    }
}