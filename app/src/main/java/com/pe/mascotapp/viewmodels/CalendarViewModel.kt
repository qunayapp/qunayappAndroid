package com.pe.mascotapp.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pe.mascotapp.domain.usecases.GetRemindersByDate
import com.pe.mascotapp.domain.usecases.GetRemindersWithPetsUseCase
import com.pe.mascotapp.vistas.adapters.ReminderPetsJoinEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getRemindersWithPetsUseCase: GetRemindersWithPetsUseCase,
    private val getRemindersByDate: GetRemindersByDate
) : ViewModel() {
    var originalReminders = listOf<ReminderPetsJoinEntity>()
    private var getRemindersJob: Job? = null
    private val _listReminders = MutableLiveData<List<ReminderPetsJoinEntity>>()
    val listReminders: LiveData<List<ReminderPetsJoinEntity>> = _listReminders
    private val _listFilteredReminders = MutableLiveData<List<ReminderPetsJoinEntity>>()
    val listFilteredReminders: LiveData<List<ReminderPetsJoinEntity>> = _listFilteredReminders
    val remindersIsEmpty: ObservableBoolean = ObservableBoolean(true)
    fun getReminders(pageNumber: Int, filterDate: LocalDate) {
        if (pageNumber == 0) originalReminders = listOf()
        getRemindersJob?.cancel()
        getRemindersJob = getRemindersWithPetsUseCase()
            .onEach { reminders ->
                if (pageNumber != 0 && reminders.isEmpty()) return@onEach
                originalReminders = reminders.map {
                    ReminderPetsJoinEntity(it)
                }
                remindersIsEmpty.set(originalReminders.isEmpty())
                _listReminders.postValue(originalReminders)
                getFilterReminders(filterDate)
            }
            .launchIn(viewModelScope)
    }

    fun getFilterReminders(filterDate: LocalDate) {
        val filteredReminders = getRemindersByDate(originalReminders, filterDate)
        _listFilteredReminders.postValue(filteredReminders)

    }
}
