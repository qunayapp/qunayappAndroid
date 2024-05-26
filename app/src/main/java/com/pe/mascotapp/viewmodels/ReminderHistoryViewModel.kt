package com.pe.mascotapp.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pe.mascotapp.domain.usecases.GetPetsUseCase
import com.pe.mascotapp.domain.usecases.GetRemindersWithPetsUseCase
import com.pe.mascotapp.domain.usecases.UpdateReminderUseCase
import com.pe.mascotapp.vistas.adapters.ReminderEntity
import com.pe.mascotapp.vistas.adapters.ReminderPetsJoinEntity
import com.pe.mascotapp.vistas.entities.TabAnimalEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderHistoryViewModel
    @Inject
    constructor(
        private val getPetsUseCase: GetPetsUseCase,
        private val getRemindersWithPetsUseCase: GetRemindersWithPetsUseCase,
        private val updateReminderUseCase: UpdateReminderUseCase,
    ) : ViewModel() {
        val remindersIsEmpty: ObservableBoolean = ObservableBoolean(true)

        private var getPetsJob: Job? = null
        private var getRemindersJob: Job? = null
        private var originalReminders = listOf<ReminderPetsJoinEntity>()

        private val _listPets = MutableLiveData<List<TabAnimalEntity>>()
        val listPets: LiveData<List<TabAnimalEntity>> = _listPets

        private val _listReminders = MutableLiveData<List<ReminderPetsJoinEntity>>()
        val listReminders: LiveData<List<ReminderPetsJoinEntity>> = _listReminders

        init {
            remindersIsEmpty.set(true)
        }

        fun getAnimalTabs() {
            getPetsJob?.cancel()
            getPetsJob =
                getPetsUseCase()
                    .onEach { pets ->
                        var listTempPets = listOf(TabAnimalEntity(null, true, "Todos", ""))
                        listTempPets = listTempPets +
                            pets.map {
                                TabAnimalEntity(it.petId, false, it.name, it.image)
                            }
                        _listPets.postValue(listTempPets)
                    }
                    .launchIn(viewModelScope)
        }

        fun cancelReminder(){
            getRemindersJob?.cancel()
        }

        fun getReminders(pageNumber: Int) {
            getRemindersJob?.cancel()
            getRemindersJob =
                getRemindersWithPetsUseCase(pageNumber)
                    .onEach { reminders ->
                        if (pageNumber == 0) {
                            originalReminders = listOf()
                        }
                        if (pageNumber != 0 && reminders.isEmpty()) {
                            return@onEach
                        }
                        originalReminders = reminders.map { ReminderPetsJoinEntity(it) }
                        remindersIsEmpty.set(originalReminders.isEmpty())
                        _listReminders.postValue(originalReminders)
                    }
                    .launchIn(viewModelScope)
        }

        fun updateReminder(reminderEntity: ReminderEntity) {
            getRemindersJob?.cancel()
            viewModelScope.launch(Dispatchers.IO) {
                updateReminderUseCase.invoke(reminderEntity.toReminder())
            }
        }

    }
