package com.pe.mascotapp.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pe.mascotapp.domain.models.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminder LIMIT 30 OFFSET :pageNumber")
    fun getReminders(pageNumber: Int): Flow<List<Reminder>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReminder(reminder: Reminder): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateReminder(reminder: Reminder)
}
