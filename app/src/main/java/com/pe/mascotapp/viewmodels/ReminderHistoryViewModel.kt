package com.pe.mascotapp.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pe.mascotapp.domain.usecases.GetPetsUseCase
import com.pe.mascotapp.domain.usecases.GetRemindersPetsJoinUseCase
import com.pe.mascotapp.domain.usecases.GetRemindersWithPetsUseCase
import com.pe.mascotapp.vistas.adapters.ReminderPetsJoinEntity
import com.pe.mascotapp.vistas.entities.TabAnimalEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ReminderHistoryViewModel @Inject constructor(
    private val getPetsUseCase: GetPetsUseCase,
    private val getRemindersWithPetsUseCase: GetRemindersWithPetsUseCase,
    private val getRemindersPetsJoinUseCase: GetRemindersPetsJoinUseCase
) : ViewModel() {

    val remindersIsEmpty: ObservableBoolean = ObservableBoolean(true)

    private var getPetsJob: Job? = null
    private var getRemindersJob: Job? = null

    private val _listPets = MutableLiveData<List<TabAnimalEntity>>()
    val listPets: LiveData<List<TabAnimalEntity>> = _listPets

    private val _listReminders = MutableLiveData<List<ReminderPetsJoinEntity>>()
    val listReminders: LiveData<List<ReminderPetsJoinEntity>> = _listReminders

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

    fun getReminders() {
        getRemindersJob?.cancel()
        getRemindersJob = getRemindersWithPetsUseCase(1)
            .onEach { reminders ->
                remindersIsEmpty.set(reminders.isEmpty())
                _listReminders.postValue(reminders.map { ReminderPetsJoinEntity(it) })
            }
            .launchIn(viewModelScope)
    }
}