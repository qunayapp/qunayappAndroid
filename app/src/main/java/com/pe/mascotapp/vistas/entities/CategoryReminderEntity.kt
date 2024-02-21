package com.pe.mascotapp.vistas.entities

import com.pe.mascotapp.R

sealed class CategoryReminderEntity(
    open var isSelected: Boolean,
    open val name: String,
    open val image: Int
) {
    object VaccineReminder : CategoryReminderEntity(
        true,
        "Vacuna",
        R.drawable.ic_vaccine
    )

    object Deworming : CategoryReminderEntity(
        false,
        "Desparasitación",
        R.drawable.ic_bacterias
    )

    object VetReminder : CategoryReminderEntity(
        false,
        "Veterinario",
        R.drawable.ic_vet
    )

    object AnalysisReminder : CategoryReminderEntity(
        false,
        "Análisis",
        R.drawable.ic_analysis
    )

    object MedicineReminder : CategoryReminderEntity(
        false,
        "Medicina",
        R.drawable.ic_medicine
    )

    object DentalReminder : CategoryReminderEntity(
        false,
        "Profilaxis Dental",
        R.drawable.ic_dental_prophylaxis
    )

    object WalkReminder : CategoryReminderEntity(
        false,
        "Paseo",
        R.drawable.ic_walk
    )

    object TakeShowerReminder : CategoryReminderEntity(
        false,
        "Baño y Corte",
        R.drawable.ic_take_shower
    )

    object WaterFoodReminder : CategoryReminderEntity(
        false,
        "Agua y Comida",
        R.drawable.ic_water_food
    )

    object OthersReminder : CategoryReminderEntity(
        false,
        "Otros",
        R.drawable.ic_others
    )

    companion object {
        fun getCategories():List<CategoryReminderEntity>{
            return listOf(
                VaccineReminder,
                Deworming,
                VetReminder,
                AnalysisReminder,
                MedicineReminder,
                DentalReminder,
                WalkReminder,
                TakeShowerReminder,
                WaterFoodReminder,
                OthersReminder
            )
        }
    }
}