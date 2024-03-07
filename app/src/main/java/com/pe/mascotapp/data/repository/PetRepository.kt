package com.pe.mascotapp.data.repository

import com.pe.mascotapp.domain.models.Pet
import kotlinx.coroutines.flow.Flow

interface PetRepository {
    suspend fun insertPet(pet: Pet)
    fun getPets(): Flow<List<Pet>>
}