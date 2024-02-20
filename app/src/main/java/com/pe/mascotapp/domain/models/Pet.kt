package com.pe.mascotapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pet(
    @PrimaryKey(autoGenerate = true)
    val petId: Int? = null,
    val image: Int,
    val name: String,
    val specie: String,
    val raza: String,
    val weight: Double,
    val sex: Sex,
    val birthdate: String
)

enum class Sex {
    FEMALE, MALE
}