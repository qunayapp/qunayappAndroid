package com.pe.mascotapp.data.data_source

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.pe.mascotapp.domain.models.ReminderWithPets
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderPetJoinDao {
    @Transaction
    @Query("SELECT * FROM reminder ORDER BY date LIMIT 30 OFFSET :pageNumber")
    fun getReminders(pageNumber: Int): Flow<List<ReminderWithPets>>
}
