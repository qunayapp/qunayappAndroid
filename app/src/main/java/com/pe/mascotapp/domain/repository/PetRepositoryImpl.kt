package com.pe.mascotapp.domain.repository

import com.pe.mascotapp.data.data_source.PetDao
import com.pe.mascotapp.data.repository.PetRepository
import com.pe.mascotapp.domain.models.Pet
import kotlinx.coroutines.flow.Flow

class PetRepositoryImpl(
    private val petDao: PetDao
) : PetRepository {
    override suspend fun insertPet(pet: Pet) {
        petDao.insertPet(pet)
    }

    override fun getPets(): Flow<List<Pet>> {
        return petDao.getPets()
    }
}