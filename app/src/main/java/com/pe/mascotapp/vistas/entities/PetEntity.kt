package com.pe.mascotapp.vistas.entities

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import com.pe.mascotapp.domain.models.Pet
import com.pe.mascotapp.domain.models.Sex
import kotlinx.parcelize.Parcelize

@Parcelize
class PetEntity(
    val petId: Long? = null,
    val image: String,
    val name: String,
    val specie: String,
    val raza: String,
    val weight: Double,
    val sex: Sex,
    val birthdate: String,
    var isSelected: Boolean,
    val color: Long = 0xFFE9E9E9
) : Parcelable {
    fun toPet(): Pet {
        return Pet(
            petId,
            image,
            name,
            specie,
            raza,
            weight,
            sex,
            birthdate,
        )
    }
}
