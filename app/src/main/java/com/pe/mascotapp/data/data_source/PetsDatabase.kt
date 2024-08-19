package com.pe.mascotapp.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pe.mascotapp.domain.models.Converters
import com.pe.mascotapp.domain.models.Pet
import com.pe.mascotapp.domain.models.Reminder
import com.pe.mascotapp.domain.models.ReminderPetJoin

@Database(
    entities = [Pet::class, Reminder::class, ReminderPetJoin::class],
    version = 18,
)
@TypeConverters(Converters::class)
abstract class PetsDatabase : RoomDatabase() {
    abstract val petDao: PetDao
    abstract val reminderDao: ReminderDao
    abstract val reminderPetJoinDao: ReminderPetJoinDao

    companion object {
        const val DATABASE_NAME = "pets_db"
    }
}
