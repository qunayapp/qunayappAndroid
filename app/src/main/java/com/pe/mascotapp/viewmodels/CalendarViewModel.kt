package com.pe.mascotapp.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pe.mascotapp.domain.models.Sex
import com.pe.mascotapp.domain.usecases.GetRemindersWithPetsUseCase
import com.pe.mascotapp.utils.CalendarUtils
import com.pe.mascotapp.vistas.adapters.ReminderEntity
import com.pe.mascotapp.vistas.adapters.ReminderPetsJoinEntity
import com.pe.mascotapp.vistas.entities.PetEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getRemindersWithPetsUseCase: GetRemindersWithPetsUseCase,
) : ViewModel() {
    var originalReminders = listOf<ReminderPetsJoinEntity>()
    private var getRemindersJob: Job? = null
    private val _listReminders = MutableLiveData<List<ReminderPetsJoinEntity>>()
    val listReminders: LiveData<List<ReminderPetsJoinEntity>> = _listReminders
    private val _listFilteredReminders= MutableLiveData<List<ReminderPetsJoinEntity>>()
    val listFilteredReminders: LiveData<List<ReminderPetsJoinEntity>> = _listFilteredReminders
    val remindersIsEmpty: ObservableBoolean = ObservableBoolean(true)
    fun getReminders(pageNumber: Int,filterDate: LocalDate) {
        if (pageNumber == 0) originalReminders = listOf()
        getRemindersJob?.cancel()
        getRemindersJob = getRemindersWithPetsUseCase(pageNumber)
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
        val filteredReminders = originalReminders.filter { reminder ->
                    // Add your condition here, for example:
                    filterDate == CalendarUtils.convertStringFormatToLocalDate(reminder.reminder.startDate, CalendarUtils.CONST_FORMAT)
                }.sortedBy { CalendarUtils.parseDate( "${it.reminder.startHour} ${it.reminder.startDate}") }

        _listFilteredReminders.postValue(filteredReminders)

    }
}
