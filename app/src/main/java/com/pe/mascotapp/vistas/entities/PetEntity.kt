package com.pe.mascotapp.vistas.entities

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import com.pe.mascotapp.domain.models.Pet
import com.pe.mascotapp.domain.models.Sex
import com.pe.mascotapp.vistas.fragments.stepRegister.BreedCategory
import com.pe.mascotapp.vistas.fragments.stepRegister.BreedPet
import com.pe.mascotapp.vistas.fragments.stepRegister.KindPet
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class PetEntity(
    val petId: Long? = null,
    val image: String = "",
    var name: String = "",
    var specie: String = "",
    var raza: String = "",
    var weight: Double = -1.0,
    var sex: Sex = Sex.NONE,
    var birthdate: String = "",
    var isSelected: Boolean = false,
    val color: Long = getRandomColor(listOf(0xFF48A7D3, 0xFF2A6BAF, 0xFF203E6C))
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

fun getRandomColor(colors: List<Long>): Long {
    val randomIndex = Random.nextInt(colors.size)
    return colors[randomIndex]
}