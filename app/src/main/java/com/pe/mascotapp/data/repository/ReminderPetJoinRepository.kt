package com.pe.mascotapp.data.repository

import com.pe.mascotapp.domain.models.ReminderPetJoin
import com.pe.mascotapp.domain.models.ReminderWithPets
import kotlinx.coroutines.flow.Flow

interface ReminderPetJoinRepository {
    fun getReminderPet(): Flow<List<ReminderWithPets>>

    fun insertReminderPet(reminder: ReminderPetJoin)

    fun getReminders(pageNumber: Int): Flow<List<ReminderPetJoin>>

    fun getAllReminders(): Flow<List<ReminderWithPets>>

    fun deleteReminder(reminderId: Long)
}
