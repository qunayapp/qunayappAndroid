package com.pe.mascotapp.vistas.entities

import com.pe.mascotapp.R

sealed class CategoryReminderEntity(
    open var isSelected: Boolean,
    open val name: String,
    open val image: Int
) {
    class VaccineReminder : CategoryReminderEntity(
        false,
        "Vacuna",
        R.drawable.ic_vaccine
    )

    class Deworming : CategoryReminderEntity(
        false,
        "Desparasitación",
        R.drawable.ic_bacterias
    )

    class VetReminder : CategoryReminderEntity(
        false,
        "Veterinario",
        R.drawable.ic_vet
    )

    class AnalysisReminder : CategoryReminderEntity(
        false,
        "Análisis",
        R.drawable.ic_analysis
    )

    class MedicineReminder : CategoryReminderEntity(
        false,
        "Medicina",
        R.drawable.ic_medicine
    )

    class DentalReminder : CategoryReminderEntity(
        false,
        "Profilaxis Dental",
        R.drawable.ic_dental_prophylaxis
    )

    class WalkReminder : CategoryReminderEntity(
        false,
        "Paseo",
        R.drawable.ic_walk
    )

    class TakeShowerReminder : CategoryReminderEntity(
        false,
        "Baño y Corte",
        R.drawable.ic_take_shower
    )

    class WaterFoodReminder : CategoryReminderEntity(
        false,
        "Agua y Comida",
        R.drawable.ic_water_food
    )

    class OthersReminder : CategoryReminderEntity(
        false,
        "Otros",
        R.drawable.ic_others
    )

    companion object {
        fun getCategories(): List<CategoryReminderEntity> {
            return listOf(
                VaccineReminder(),
                Deworming(),
                VetReminder(),
                AnalysisReminder(),
                MedicineReminder(),
                DentalReminder(),
                WalkReminder(),
                TakeShowerReminder(),
                WaterFoodReminder(),
                OthersReminder()
            )
        }
    }
}