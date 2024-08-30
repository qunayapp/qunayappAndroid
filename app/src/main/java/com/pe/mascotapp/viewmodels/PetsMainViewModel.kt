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

        return      listOf(
            PetEntity(
                null,
                "https://www.telegraph.co.uk/content/dam/news/2023/06/10/TELEMMGLPICT000296384999_16864028803870_trans_NvBQzQNjv4BqrCS9JVgwgb8GODK1xmD4xlHwtdpQwyNje2OyIL7x97s.jpeg", "Paul Pugba1",
                "Perro",
                "100.00",
                Sex.MALE,
                birthdate = "12/02/2010",
                false
            ),
            PetEntity(
                null,
                "https://static01.nyt.com/images/2024/01/16/multimedia/16xp-dog-01-lchw/16xp-dog-01-lchw-videoSixteenByNineJumbo1600.jpg",
                "Paul",
                "Perro",
                "101.00",
                Sex.MALE,
                birthdate = "12/02/2010",
                false
            ),
            PetEntity(
                null,
                "https://cdn.britannica.com/79/232779-050-6B0411D7/German-Shepherd-dog-Alsatian.jpg", "Paul Pugba3",
                "Perro",
                "102.00",
                Sex.MALE,
                birthdate = "12/02/2010",
                false
            )
        )

    }
}