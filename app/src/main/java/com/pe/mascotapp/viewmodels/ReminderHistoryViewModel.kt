package com.pe.mascotapp.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pe.mascotapp.domain.usecases.GetPetsUseCase
import com.pe.mascotapp.domain.usecases.GetRemindersWithPetsUseCase
import com.pe.mascotapp.vistas.adapters.ReminderEntity
import com.pe.mascotapp.vistas.entities.TabAnimalEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ReminderHistoryViewModel @Inject constructor(
    private val getPetsUseCase: GetPetsUseCase,
    private val getRemindersWithPetsUseCase: GetRemindersWithPetsUseCase
) : ViewModel() {

    val remindersIsEmpty: ObservableBoolean = ObservableBoolean(true)

    private var getPetsJob: Job? = null

    private val _listPets = MutableLiveData<List<TabAnimalEntity>>()
    val listPets: LiveData<List<TabAnimalEntity>> = _listPets

    init {
        remindersIsEmpty.set(true)
    }

    fun getAnimalTabs() {
        getRemindersWithPetsUseCase(1)
        getPetsJob?.cancel()
        getPetsJob = getPetsUseCase()
            .onEach { pets ->
                _listPets.postValue(pets.map {
                    TabAnimalEntity(it.petId, false, it.name, it.image)
                })
            }
            .launchIn(viewModelScope)
    }

    fun getReminders(): List<ReminderEntity> {

        return listOf()
        //return reminder
    }
}