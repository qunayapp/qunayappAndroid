package com.pe.mascotapp.domain.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["reminderId", "petId"])
class ReminderPetJoin(
    val reminderId: String,
    val petId: String
)


data class ReminderWithPets(
    @Embedded
    var reminder: Reminder,
    @Relation(
        parentColumn = "reminderId",
        entity = Pet::class,
        entityColumn = "petId",
        associateBy = Junction(
            value = ReminderPetJoin::class,
            parentColumn = "reminderId",
            entityColumn = "petId"
        )
    )
    var pets: List<Pet>
)