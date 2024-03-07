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
    @Query("SELECT * FROM reminder ORDER BY date LIMIT 30 OFFSET :pageNumber")
    fun getReminderPet(pageNumber: Int): Flow<List<ReminderWithPets>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(join: ReminderPetJoin)
}
