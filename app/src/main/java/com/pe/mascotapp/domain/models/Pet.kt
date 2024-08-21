package com.pe.mascotapp.domain.models

import androidx.compose.ui.graphics.Color
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.pe.mascotapp.vistas.entities.PetEntity

@Entity
data class Pet(
    @PrimaryKey(autoGenerate = true)
    val petId: Long? = null,
    val image: String,
    val name: String,
    val specie: String,
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
            weight.toString(),
            sex,
            birthdate,
            false
        )
    }
}

enum class Sex {
    FEMALE, MALE, NONE;

    fun toSpanish(): String {
        return when (this) {
            FEMALE -> "Hembra"
            MALE -> "Macho"
            NONE -> "Ninguno"
        }
    }
}

@Entity
data class Breed(
    @PrimaryKey(autoGenerate = true)
    val breedId: Long? = null,
    val name: String
)

@Entity(
    primaryKeys = ["breedId", "petId"]
)
data class BreedPetJoin(
    val breedId: Long,
    val petId: Long
)

data class PetsWithBreeds(
    @Embedded
    var pet: Pet,
    @Relation(
        parentColumn = "petId",
        entity = Pet::class,
        entityColumn = "breedId",
        associateBy = Junction(
            value = PetsWithBreeds::class,
            parentColumn = "petId",
            entityColumn = "breedId"
        )
    )
    var pets: List<Breed>
)
