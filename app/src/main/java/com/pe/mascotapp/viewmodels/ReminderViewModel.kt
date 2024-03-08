package com.pe.mascotapp.viewmodels

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pe.mascotapp.domain.models.Pet
import com.pe.mascotapp.domain.models.Sex
import com.pe.mascotapp.domain.usecases.GetPetsUseCase
import com.pe.mascotapp.domain.usecases.InsertPetUseCase
import com.pe.mascotapp.domain.usecases.InsertReminderUseCase
import com.pe.mascotapp.domain.usecases.InsertReminderWithPetsUseCase
import com.pe.mascotapp.utils.CalendarUtils
import com.pe.mascotapp.vistas.adapters.CalendarHourOption
import com.pe.mascotapp.vistas.adapters.CalendarOptionNormal
import com.pe.mascotapp.vistas.adapters.CalendarSimple
import com.pe.mascotapp.vistas.adapters.CounterOption
import com.pe.mascotapp.vistas.adapters.OptionViewInterface
import com.pe.mascotapp.vistas.adapters.ReminderEntity
import com.pe.mascotapp.vistas.adapters.ScheduleOption
import com.pe.mascotapp.vistas.adapters.TextOption
import com.pe.mascotapp.vistas.adapters.ValueTextOption
import com.pe.mascotapp.vistas.entities.CategoryReminderEntity
import com.pe.mascotapp.vistas.entities.PetEntity
import com.pe.mascotapp.vistas.entities.VaccineFieldEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReminderViewModel @Inject constructor(
    val insertReminderWithPetsUseCase: InsertReminderWithPetsUseCase,
    val insertReminderUseCase: InsertReminderUseCase,
    val insertPetUseCase: InsertPetUseCase,
    val getPetUseCase: GetPetsUseCase
) : ViewModel() {

    private val _categoriesReminder = MutableLiveData<List<CategoryReminderEntity>>()
    val categoriesReminder: LiveData<List<CategoryReminderEntity>> = _categoriesReminder

    private val _listPets = MutableLiveData<List<PetEntity>>()
    val listPets: LiveData<List<PetEntity>> = _listPets

    private var getPetsJob: Job? = null

    private var nameEvent: String? = null

    private var allDay: Boolean = false

    private val reminderEntity: ReminderEntity = ReminderEntity()

    val listVaccines = mutableListOf(VaccineFieldEntity())

    val enableForm: ObservableBoolean = ObservableBoolean(false)

    private val _listOptionsRepeat = MutableLiveData<List<OptionViewInterface>>()
    val listOptionsRepeat: LiveData<List<OptionViewInterface>> = _listOptionsRepeat

    private val _listDurationRepeat = MutableLiveData<List<OptionViewInterface>>()
    val listDurationRepeat: LiveData<List<OptionViewInterface>> = _listDurationRepeat

    private val _listAlarms = MutableLiveData<ArrayList<List<OptionViewInterface>>>()
    val listAlarms: LiveData<ArrayList<List<OptionViewInterface>>> = _listAlarms

    private val _optionStartHour = MutableLiveData<List<OptionViewInterface>>()
    val optionStartHour: LiveData<List<OptionViewInterface>> = _optionStartHour

    private val _optionEndHour = MutableLiveData<List<OptionViewInterface>>()
    val optionEndHour: LiveData<List<OptionViewInterface>> = _optionEndHour

    private val _optionStartDate = MutableLiveData<List<OptionViewInterface>>()
    val optionStartDate: LiveData<List<OptionViewInterface>> = _optionStartDate

    private val _optionEndDate = MutableLiveData<List<OptionViewInterface>>()
    val optionEndDate: LiveData<List<OptionViewInterface>> = _optionEndDate

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

    fun setAllDay(allDay: Boolean) {
        this.allDay = allDay
    }

    fun getPets() {
        getPetsJob?.cancel()
        getPetsJob = getPetUseCase()
            .onEach { pets ->
                _listPets.postValue(pets.map { it.toPetEntity() })
            }
            .launchIn(viewModelScope)
        getData()
        setData()
    }

    private fun getData() {
        _listPets.postValue(
            listOf(
                PetEntity(
                    null,
                    "https://www.telegraph.co.uk/content/dam/news/2023/06/10/TELEMMGLPICT000296384999_16864028803870_trans_NvBQzQNjv4BqrCS9JVgwgb8GODK1xmD4xlHwtdpQwyNje2OyIL7x97s.jpeg", "Paul Pugba1",
                    "Perro",
                    "Especial",
                    100.00,
                    Sex.MALE,
                    birthdate = "12/02/2010",
                    false
                ),
                PetEntity(
                    null,
                    "https://static01.nyt.com/images/2024/01/16/multimedia/16xp-dog-01-lchw/16xp-dog-01-lchw-videoSixteenByNineJumbo1600.jpg",
                    "Paul",
                    "Perro",
                    "Especial",
                    101.00,
                    Sex.MALE,
                    birthdate = "12/02/2010",
                    false
                ),
                PetEntity(
                    null,
                    "https://cdn.britannica.com/79/232779-050-6B0411D7/German-Shepherd-dog-Alsatian.jpg", "Paul Pugba3",
                    "Perro",
                    "Especial",
                    102.00,
                    Sex.MALE,
                    birthdate = "12/02/2010",
                    false
                )
            )
        )
    }

    private fun setData() {
        viewModelScope.launch {
            val pets = listOf(
                PetEntity(
                    null,
                    "https://www.telegraph.co.uk/content/dam/news/2023/06/10/TELEMMGLPICT000296384999_16864028803870_trans_NvBQzQNjv4BqrCS9JVgwgb8GODK1xmD4xlHwtdpQwyNje2OyIL7x97s.jpeg", "Paul Pugba1",
                    "Perro",
                    "Especial",
                    100.00,
                    Sex.MALE,
                    birthdate = "12/02/2010",
                    false
                ),
                PetEntity(
                    null,
                    "https://static01.nyt.com/images/2024/01/16/multimedia/16xp-dog-01-lchw/16xp-dog-01-lchw-videoSixteenByNineJumbo1600.jpg",
                    "Paul",
                    "Perro",
                    "Especial",
                    101.00,
                    Sex.MALE,
                    birthdate = "12/02/2010",
                    false
                ),
                PetEntity(
                    null,
                    "https://cdn.britannica.com/79/232779-050-6B0411D7/German-Shepherd-dog-Alsatian.jpg", "Paul Pugba3",
                    "Perro",
                    "Especial",
                    102.00,
                    Sex.MALE,
                    birthdate = "12/02/2010",
                    false
                )
            )
            pets.forEach {
                Log.e("quack", "Asdf")
                insertPetUseCase(
                    Pet(
                        image = it.image,
                        name = it.name,
                        specie = it.specie,
                        weight = it.weight,
                        sex = it.sex,
                        raza = it.raza,
                        birthdate = it.birthdate
                    )
                )
            }
        }

    }

    fun getOptionsRepeat() {
        _listOptionsRepeat.postValue(
            listOf(
                TextOption("No Repetir", ValueTextOption.DONT_REPEAT),
                TextOption("Todos los dias", ValueTextOption.ALL_DAYS),
                TextOption("De lunes a Viernes", ValueTextOption.MONDAY_FRIDAY),
                TextOption("Todas las semanas", ValueTextOption.ALL_WEEKS),
                TextOption("Todos los meses", ValueTextOption.ALL_MONTHS),
                TextOption("Todos los años", ValueTextOption.ALL_YEARS)
            )
        )
    }

    fun getAlarmOptions() {
        _listAlarms.postValue(
            arrayListOf(
                listOf(
                    TextOption("15 minutos antes", ValueTextOption.MINUTES_15),
                    TextOption("30 minutos antes", ValueTextOption.MINUTES_30),
                    TextOption("1 hora antes", ValueTextOption.MINUTES_HOUR),
                    CalendarHourOption("Personalizar")
                )
            )
        )
    }

    fun getOptionsDurationRepeat() {
        _listDurationRepeat.postValue(
            listOf(
                TextOption("Para siempre", ValueTextOption.FOR_EVER),
                CounterOption("Numero de veces"),
                CalendarOptionNormal("Hasta el dia")
            )
        )
    }


    fun getOptionStartDate() {
        _optionStartDate.postValue(
            listOf(
                CalendarSimple("Seleccionar Horario Inicio")
            )
        )
    }

    fun getOptionEndDate() {
        _optionEndDate.postValue(
            listOf(
                CalendarSimple("Seleccionar Horario Inicio")
            )
        )
    }

    fun getOptionEndHour() {
        _optionEndHour.postValue(
            listOf(
                ScheduleOption("Seleccionar Horario Final")
            )
        )
    }

    fun getOptionStartHour() {
        _optionStartHour.postValue(
            listOf(
                ScheduleOption("Seleccionar Horario Final")
            )
        )
    }


    fun getEndHourSelected(): String? {
        return (_optionEndHour.value?.firstOrNull() as ScheduleOption?)?.hour?.let {
            reminderEntity.endHour = it
            it
        }
    }

    fun getStartHourSelected(): String? {
        return (_optionStartHour.value?.firstOrNull() as ScheduleOption?)?.hour?.let {
            reminderEntity.startHour = it
            it
        }
    }

    fun getStartDateSelected(): String? {
        return (_optionStartDate.value?.firstOrNull() as CalendarSimple?)?.date?.let { CalendarUtils.getFormatDate(it) }?.let {
            reminderEntity.startDate = it
            it
        }
    }

    fun getEndDateSelected(): String? {
        return (_optionEndDate.value?.firstOrNull() as CalendarSimple?)?.date?.let { CalendarUtils.getFormatDate(it) }
    }


    fun getOptionRepeat(): String? {
        return (_listOptionsRepeat.value?.firstOrNull { it.isSelected } as TextOption?)?.name
    }

    fun getDurationRepeat(): String? {
        return _listDurationRepeat.value?.firstOrNull { it.isSelected }?.let { option ->
            when (option) {
                is TextOption -> option.name
                is CalendarOptionNormal -> option.date?.let { "hasta el " + CalendarUtils.getFormatDate2(it) }
                is CounterOption -> option.counter.toString() + " veces"
                else -> null
            }
        }
    }

    fun getAlarms(): String? {
        reminderEntity.alarms = arrayListOf()
        reminderEntity.dateAlarms = arrayListOf()
        val options = _listAlarms.value?.flatMap { it.filter { option -> option.isSelected } }
        val strings = options?.mapNotNull {
            when (it) {
                is TextOption -> {
                    reminderEntity.alarms.add(it.value.name)
                    it.name
                }

                is CalendarHourOption -> {
                    it.date?.let { date ->
                        {
                            val dateFormat = "${CalendarUtils.getFormatDate2(date)} ${it.hour}"
                            reminderEntity.dateAlarms.add(dateFormat)
                            dateFormat
                        }
                    }
                }

                else -> null
            }
        }
        return strings?.joinToString(",")
    }

    fun validateForm(): Boolean {
        if (!enableForm.get()) return false
        if (nameEvent?.isEmpty() == true) return false
        val optionRepeat = _listOptionsRepeat.value?.firstOrNull { it is TextOption && it.isSelected } ?: return false
        val duration = _listDurationRepeat.value?.forEach {

        }
        return false
    }

    fun setNameReminder(name: String) {
        reminderEntity.title = name
    }

    fun createReminder() {
    }
}
