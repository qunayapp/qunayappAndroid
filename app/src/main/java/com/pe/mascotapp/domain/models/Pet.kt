package com.pe.mascotapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pe.mascotapp.vistas.entities.PetEntity

@Entity
data class Pet(
    @PrimaryKey(autoGenerate = true)
    val petId: Long? = null,
    val image: String,
    val name: String,
    val specie: String,
    val raza: String,
    val weight: Double,
    val sex: Sex,
    val birthdate: String
) {
    fun toPetEntity(): PetEntity {
        return PetEntity(
            petId,
            image,
            name,
            specie,
            raza,
            weight,
            sex,
            birthdate,
            false
        )
    }
}

enum class Sex {
    FEMALE, MALE
}