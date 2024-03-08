package com.pe.mascotapp.domain.usecases

import com.pe.mascotapp.data.repository.PetRepository
import com.pe.mascotapp.domain.models.Pet
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPetsUseCase @Inject constructor(
    private val petRepository: PetRepository
) {
    operator fun invoke(): Flow<List<Pet>> {
        return petRepository.getPets()
    }
}