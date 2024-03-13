package com.pe.mascotapp.viewmodels

import androidx.lifecycle.ViewModel
import com.pe.mascotapp.domain.models.Sex
import com.pe.mascotapp.vistas.adapters.ReminderEntity
import com.pe.mascotapp.vistas.adapters.ReminderPetsJoinEntity
import com.pe.mascotapp.vistas.entities.PetEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor() : ViewModel() {
    fun getReminders(): List<ReminderPetsJoinEntity> {

        return listOf(
            ReminderPetsJoinEntity(
                pets =                 listOf(
                    PetEntity(
                        null,
                        "https://www.telegraph.co.uk/content/dam/news/2023/06/10/TELEMMGLPICT000296384999_16864028803870_trans_NvBQzQNjv4BqrCS9JVgwgb8GODK1xmD4xlHwtdpQwyNje2OyIL7x97s.jpeg",
                        "Paul Pugba1",
                        "Perro",
                        "Especial",
                        100.00,
                        Sex.MALE,
                        birthdate = "12/02/2010",
                        false
                    )
                ),
               reminder =  ReminderEntity(null,
                    "Vacuna anti rabia",
                    "llevar historial medico y recordar preguntar sobre posibles efectos secundarios de la vacuna",
                    "15/08/201",
                    "15/08/201",
                    "11:05",
                    "13:05",
                    true,
                    "Clinica Delgado, calle tal",
                    null,
                    true,
                    arrayListOf(),
                    arrayListOf()
                )),            ReminderPetsJoinEntity(
                pets =                listOf(
                    PetEntity(
                        null,
                        "https://static01.nyt.com/images/2024/01/16/multimedia/16xp-dog-01-lchw/16xp-dog-01-lchw-videoSixteenByNineJumbo1600.jpg",
                        "Paul",
                        "Perro",
                        "Especial",
                        101.00,
                        Sex.MALE,
                        birthdate = "12/02/2010",
                        false
                    )
                ),
                reminder =  ReminderEntity(null,
                    "Desparasitación",
                    "llevar historial medico y recordar preguntar sobre posibles efectos secundarios de la vacuna",
                    "15/08/201",
                    "15/08/201",
                    "11:05",
                    "13:05",
                    true,
                    "Clinica Delgado, calle tal",
                    null,
                    false,
                    arrayListOf(),
                    arrayListOf()
                )),ReminderPetsJoinEntity(
                pets =               listOf(
                    PetEntity(
                        null,
                        "https://cdn.britannica.com/79/232779-050-6B0411D7/German-Shepherd-dog-Alsatian.jpg",
                        "Paul Pugba3",
                        "Perro",
                        "Especial",
                        102.00,
                        Sex.MALE,
                        birthdate = "12/02/2010",
                        false
                    )
                ),
                reminder = ReminderEntity(null,
                    "Exámenes médicos",
                    "llevar historial medico y recordar preguntar sobre posibles efectos secundarios de la vacuna",
                    "15/08/201",
                    "15/08/201",
                    "11:05",
                    "13:05",
                    true,
                    "Clinica Delgado, calle tal",
                    null,
                    false,
                    arrayListOf(),
                    arrayListOf()
                )))

        //return reminder
    }
}
