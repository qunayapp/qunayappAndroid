package com.pe.mascotapp.domain.usecases

import com.pe.mascotapp.data.repository.PetRepository
import com.pe.mascotapp.domain.models.Pet
import javax.inject.Inject

class InsertPetUseCase @Inject constructor(
    private val petRepository: PetRepository
) {
    suspend operator fun invoke(pet: Pet) {
        petRepository.insertPet(pet)
    }
}