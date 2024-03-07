package com.pe.mascotapp.vistas.entities

import com.pe.mascotapp.domain.models.Sex

class PetEntity(
    val petId: Int? = null,
    val image: String,
    val name: String,
    val specie: String,
    val raza: String,
    val weight: Double,
    val sex: Sex,
    val birthdate: String,
    var isSelected: Boolean
){

}