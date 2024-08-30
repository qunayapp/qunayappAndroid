package com.pe.mascotapp.vistas.entities

import android.os.Parcelable
import android.util.Log
import com.pe.mascotapp.R
import com.pe.mascotapp.domain.models.Pet
import com.pe.mascotapp.domain.models.Sex
import com.pe.mascotapp.vistas.fragments.stepRegister.BreedPetEntity
import com.pe.mascotapp.vistas.fragments.stepRegister.KindPet
import com.pe.mascotapp.vistas.fragments.stepRegister.value
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class PetEntity(
    val petId: Long? = null,
    val image: String = "",
    var name: String = "",
    var specie: String = KindPet.None.value(),
    var weight: String = "",
    var sex: Sex = Sex.NONE,
    var birthdate: String = "",
    var isSelected: Boolean = false,
    val color: Long = 0xFF48A7D3
) : Parcelable {
    fun toPet(): Pet {
        return Pet(
            petId,
            image,
            name,
            specie,
            weight.toDouble(),
            sex,
            birthdate,
        )
    }

    fun isValid(): Boolean {
        return name != "" && specie != "" && specie != KindPet.None.value()
    }

    fun specieDrawable(): Int {
        return when (specie) {
            KindPet.Dog.value() -> R.drawable.perro
            KindPet.Cat.value() -> R.drawable.gato
            KindPet.Other.value() -> R.drawable.llama
            else -> R.drawable.llama
        }
    }


}

@Parcelize
data class PetWithBreedsEntity(
    val pet: PetEntity,
    var breeds: List<BreedPetEntity>
) : Parcelable
