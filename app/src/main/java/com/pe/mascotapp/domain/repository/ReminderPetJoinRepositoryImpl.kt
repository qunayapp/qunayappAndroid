package com.pe.mascotapp.domain.repository

import com.pe.mascotapp.data.data_source.ReminderPetJoinDao
import com.pe.mascotapp.data.repository.ReminderPetJoinRepository
import com.pe.mascotapp.domain.models.ReminderWithPets
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderPetJoinRepositoryImpl @Inject constructor(
    private val reminderPetJoinDao: ReminderPetJoinDao
) : ReminderPetJoinRepository {

    override fun getReminders(pageNumber: Int): Flow<List<ReminderWithPets>> {
        return reminderPetJoinDao.getReminders(pageNumber)
    }
}