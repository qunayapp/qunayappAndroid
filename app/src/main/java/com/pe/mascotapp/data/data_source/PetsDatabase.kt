package com.pe.mascotapp.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pe.mascotapp.domain.models.Pet
import com.pe.mascotapp.domain.models.Reminder
import com.pe.mascotapp.domain.models.ReminderPetJoin
import com.pe.mascotapp.domain.models.ReminderWithPets

@Database(
    entities = [Pet::class, Reminder::class, ReminderPetJoin::class],
    version = 1
)

abstract class PetsDatabase : RoomDatabase() {
    abstract val petDao: PetDao
    abstract val reminderDao: ReminderDao
    abstract val reminderPetJoinDao: ReminderPetJoinDao

    companion object {
        const val DATABASE_NAME = "pets_db"
    }
}