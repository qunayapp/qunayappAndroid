package com.pe.mascotapp.data.repository

import com.pe.mascotapp.domain.models.ReminderWithPets
import kotlinx.coroutines.flow.Flow

interface ReminderPetJoinRepository {
    fun getReminders(pageNumber: Int): Flow<List<ReminderWithPets>>
}