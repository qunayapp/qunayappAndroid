package com.pe.mascotapp.viewmodels

import androidx.lifecycle.ViewModel
import com.pe.mascotapp.R
import com.pe.mascotapp.vistas.adapters.ReminderEntity
import com.pe.mascotapp.vistas.adapters.TabAnimalEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReminderHistoryViewModel @Inject constructor(

) : ViewModel() {
    fun getAnimalTabs(): List<TabAnimalEntity> {
        return listOf(
            TabAnimalEntity(true, "Todos", R.drawable.ic_baseline_person_24),
            TabAnimalEntity(false, "Gattuso", R.drawable.perro1),
            TabAnimalEntity(false, "Harry Cane", R.drawable.perro1),
        )
    }

    fun getReminders(): List<ReminderEntity> {
        return listOf(
            ReminderEntity("asdf", "asdf", "asdf", "adf", "asdf", R.drawable.ic_vaccine),
            ReminderEntity("asdf", "asdf", "asdf", "adf", "asdf", R.drawable.ic_vaccine, false),
        )
    }
}