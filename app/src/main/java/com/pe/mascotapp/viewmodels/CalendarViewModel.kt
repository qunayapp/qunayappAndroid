package com.pe.mascotapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pe.mascotapp.domain.usecases.GetRemindersByDate
import com.pe.mascotapp.domain.usecases.GetRemindersWithPetsUseCase
import com.pe.mascotapp.vistas.adapters.ReminderPetsJoinEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getRemindersWithPetsUseCase: GetRemindersWithPetsUseCase,
    private val getRemindersByDate: GetRemindersByDate
) : ViewModel() {

    private var originalReminders = listOf<ReminderPetsJoinEntity>()

    var reminderByDate: ConcurrentHashMap<LocalDate, List<ReminderPetsJoinEntity>> = ConcurrentHashMap()

    var reminderByDateCount: ConcurrentHashMap<LocalDate, Int> = ConcurrentHashMap()

    private var _selectedDate = MutableLiveData(LocalDate.now())
    val selectedDate : LiveData<LocalDate> = _selectedDate

    private var getAllRemindersJob: Job? = null

    private var jobByMonth: Job? = null

    private val _listRemindersByDateCount = MutableLiveData<Unit>()
    val listRemindersByDateCount: LiveData<Unit> = _listRemindersByDateCount

    fun updateSelectDate(selectedDate: LocalDate){
        _selectedDate.postValue(selectedDate)
    }

    fun getAllReminders(filterDate: LocalDate) {
        getAllRemindersJob?.cancel()
        getAllRemindersJob = getRemindersWithPetsUseCase()
            .map { reminders ->
                originalReminders = reminders.map { ReminderPetsJoinEntity(it) }
            }
            .onEach {
                getRemindersByMonth(filterDate = filterDate)
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun getRemindersByMonth(filterDate: LocalDate) {
        jobByMonth?.cancel()
        jobByMonth = viewModelScope.launch(Dispatchers.IO) {
            val endDateMonth = filterDate.withDayOfMonth(filterDate.lengthOfMonth())
            var startDate = filterDate
            while (startDate <= endDateMonth) {
                getReminderByDate(startDate)
                startDate = startDate.plusDays(1)
            }
            withContext(Dispatchers.Main) {
                _listRemindersByDateCount.postValue(Unit)
            }
        }
    }

    private fun getReminderByDate(filterDate: LocalDate) {
        val events = getRemindersByDate(originalReminders, filterDate)
        if (events.isNotEmpty()) {
            reminderByDate[filterDate] = events
            reminderByDateCount[filterDate] = events.size
        }
    }
}
