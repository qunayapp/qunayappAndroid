package com.pe.mascotapp.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.pe.mascotapp.domain.models.ReminderPetJoin
import com.pe.mascotapp.domain.models.ReminderWithPets
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderPetJoinDao {
    @Transaction
    @Query("SELECT * FROM reminder")
    fun getReminderPet(): Flow<List<ReminderWithPets>>

    @Transaction
    @Query("SELECT * FROM reminder WHERE isActivated")
    fun getAllReminderPet(): Flow<List<ReminderWithPets>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(join: ReminderPetJoin)

    @Query("SELECT * FROM reminderpetjoin LIMIT 30 OFFSET :pageNumber")
    fun getReminders(pageNumber: Int): Flow<List<ReminderPetJoin>>

    @Query("DELETE FROM reminderpetjoin WHERE reminderId = :reminderId")
    fun deleteReminder(reminderId: Long)
}
