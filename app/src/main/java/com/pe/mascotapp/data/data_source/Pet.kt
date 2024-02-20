package com.pe.mascotapp.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.pe.mascotapp.domain.models.Pet
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {
    @Query("SELECT * FROM pet")
    fun getPets(): Flow<List<Pet>>
}

