package com.pe.mascotapp.domain.repository

import com.pe.mascotapp.data.data_source.ReminderPetJoinDao
import com.pe.mascotapp.data.repository.ReminderPetJoinRepository
import com.pe.mascotapp.domain.models.ReminderPetJoin
import com.pe.mascotapp.domain.models.ReminderWithPets
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderPetJoinRepositoryImpl
    @Inject
    constructor(
        private val reminderPetJoinDao: ReminderPetJoinDao,
    ) : ReminderPetJoinRepository {
        override fun getReminderPet(): Flow<List<ReminderWithPets>> {
            return reminderPetJoinDao.getReminderPet()
        }

        override fun insertReminderPet(reminder: ReminderPetJoin) {
            reminderPetJoinDao.insert(reminder)
        }

        override fun getReminders(pageNumber: Int): Flow<List<ReminderPetJoin>> {
            return reminderPetJoinDao.getReminders(pageNumber)
        }

        override fun getAllReminders(): Flow<List<ReminderWithPets>> {
            return reminderPetJoinDao.getAllReminderPet()
        }

        override fun deleteReminder(reminderId: Long) {
            return reminderPetJoinDao.deleteReminder(reminderId)
        }
    }
