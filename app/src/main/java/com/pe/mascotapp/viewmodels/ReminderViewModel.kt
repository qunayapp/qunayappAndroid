package com.pe.mascotapp.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pe.mascotapp.R
import com.pe.mascotapp.domain.models.Sex
import com.pe.mascotapp.vistas.entities.CategoryReminderEntity
import com.pe.mascotapp.vistas.entities.PetEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ReminderViewModel @Inject constructor() : ViewModel() {

    private val _categoriesReminder = MutableLiveData<List<CategoryReminderEntity>>()
    val categoriesReminder: LiveData<List<CategoryReminderEntity>> = _categoriesReminder

    private val _listPets = MutableLiveData<List<PetEntity>>()
    val listPets: LiveData<List<PetEntity>> = _listPets

    val enableForm: ObservableBoolean = ObservableBoolean(false)

    init {
        enableForm.set(false)
    }

    fun getSelectCategories() {
        _categoriesReminder.postValue(CategoryReminderEntity.getCategories())
    }

    fun enableForm() {
        val atLeastPetIsSelected = listPets.value?.firstOrNull { it.isSelected } != null
        val atLeastCategoryIsSelected = categoriesReminder.value?.firstOrNull { it.isSelected } != null
        enableForm.set(atLeastPetIsSelected && atLeastCategoryIsSelected)
    }

    fun getPets() {
        _listPets.postValue(
            listOf(
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
        )
    }
}