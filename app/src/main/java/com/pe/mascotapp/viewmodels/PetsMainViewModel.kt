package com.pe.mascotapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pe.mascotapp.R
import com.pe.mascotapp.domain.models.Sex
import com.pe.mascotapp.vistas.entities.PetEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PetsMainViewModel @Inject constructor() : ViewModel() {
/*    private val _listPets = MutableLiveData<List<PetEntity>>()
    val listPets: LiveData<List<PetEntity>> = _listPets*/
    fun getPets() :List<PetEntity>{

        return    listOf(
                PetEntity(
                    1,
                    R.drawable.perro1,
                    "Paul Pugba",
                    "Perro",
                    "Especial",
                    100.00,
                    Sex.MALE,
                    "12/02/2000",
                    false
                ),
                PetEntity(
                    1,
                    R.drawable.perro1,
                    "Paul Pugba",
                    "Perro",
                    "Especial",
                    100.00,
                    Sex.MALE,
                    "12/02/2000",
                    false
                ),
                PetEntity(
                    1,
                    R.drawable.perro1,
                    "Paul Pugba",
                    "Perro",
                    "Especial",
                    100.00,
                    Sex.MALE,
                    "12/02/2000",
                    false
                ),
                PetEntity(
                    1,
                    R.drawable.perro1,
                    "Paul Pugba",
                    "Perro",
                    "Especial",
                    100.00,
                    Sex.MALE,
                    "12/02/2000",
                    false
                ),
                PetEntity(
                    1,
                    R.drawable.perro1,
                    "Paul Pugba",
                    "Perro",
                    "Especial",
                    100.00,
                    Sex.MALE,
                    "12/02/2000",
                    false
                )
            )

    }
}