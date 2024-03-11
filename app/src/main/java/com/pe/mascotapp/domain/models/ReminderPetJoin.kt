package com.pe.mascotapp.domain.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    primaryKeys = ["reminderId", "petId"]
)
data class ReminderPetJoin(
    val reminderId: Long,
    val petId: Long
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