package com.pe.mascotapp.viewmodels

import android.net.Uri
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pe.mascotapp.domain.models.Pet
import com.pe.mascotapp.domain.models.ReminderPetJoin
import com.pe.mascotapp.domain.models.ReminderWithPets
import com.pe.mascotapp.domain.models.Sex
import com.pe.mascotapp.domain.usecases.DeleteReminderWithPets
import com.pe.mascotapp.domain.usecases.GetPetsUseCase
import com.pe.mascotapp.domain.usecases.InsertPetUseCase
import com.pe.mascotapp.domain.usecases.InsertReminderUseCase
import com.pe.mascotapp.domain.usecases.InsertReminderWithPetsUseCase
import com.pe.mascotapp.domain.usecases.UpdateReminderUseCase
import com.pe.mascotapp.utils.CalendarUtils
import com.pe.mascotapp.utils.CalendarUtils.Companion.CONST_FORMAT
import com.pe.mascotapp.vistas.adapters.CalendarOptionNormal
import com.pe.mascotapp.vistas.adapters.CalendarSimple
import com.pe.mascotapp.vistas.adapters.CounterOption
import com.pe.mascotapp.vistas.adapters.OptionViewInterface
import com.pe.mascotapp.vistas.adapters.ReminderEntity
import com.pe.mascotapp.vistas.adapters.ReminderPetsJoinEntity
import com.pe.mascotapp.vistas.adapters.ScheduleOption
import com.pe.mascotapp.vistas.adapters.TextOption
import com.pe.mascotapp.vistas.adapters.TypeOption
import com.pe.mascotapp.vistas.adapters.ValueTextOption
import com.pe.mascotapp.vistas.entities.CategoryReminderEntity
import com.pe.mascotapp.vistas.entities.PetEntity
import com.pe.mascotapp.vistas.entities.VaccineFieldEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel
@Inject
constructor(
    val insertReminderWithPetsUseCase: InsertReminderWithPetsUseCase,
    val updateReminderUseCase: UpdateReminderUseCase,
    val insertReminderUseCase: InsertReminderUseCase,
    val insertPetUseCase: InsertPetUseCase,
    val getPetUseCase: GetPetsUseCase,
    val deleteReminderWithPets: DeleteReminderWithPets,
) : ViewModel() {
    private val _categoriesReminder = MutableLiveData<List<CategoryReminderEntity>>()
    val categoriesReminder: LiveData<List<CategoryReminderEntity>> = _categoriesReminder

    private val _listPets = MutableLiveData<List<PetEntity>>()
    val listPets: LiveData<List<PetEntity>> = _listPets

    private var getPetsJob: Job? = null

    private var reminderEntity: ReminderEntity = ReminderEntity()

    private var reminderPetsJoin: ReminderPetsJoinEntity =
        ReminderPetsJoinEntity(ReminderEntity(), listOf())

    var listVaccines = mutableListOf(VaccineFieldEntity())

    val enableForm: ObservableBoolean = ObservableBoolean(false)

    val enableVaccines: ObservableBoolean = ObservableBoolean(false)

    val enableButton: ObservableBoolean = ObservableBoolean(false)

    private val _listOptionsRepeat = MutableLiveData<List<OptionViewInterface>>()
    val listOptionsRepeat: LiveData<List<OptionViewInterface>> = _listOptionsRepeat

    private val _listDurationRepeat = MutableLiveData<List<OptionViewInterface>>()
    val listDurationRepeat: LiveData<List<OptionViewInterface>> = _listDurationRepeat

    private val _listAlarms = MutableLiveData<List<OptionViewInterface>>()
    val listAlarms: LiveData<List<OptionViewInterface>> = _listAlarms

    private val _optionStartHour = MutableLiveData<List<OptionViewInterface>>()
    val optionStartHour: LiveData<List<OptionViewInterface>> = _optionStartHour

    private val _optionStartDate = MutableLiveData<List<OptionViewInterface>>()
    val optionStartDate: LiveData<List<OptionViewInterface>> = _optionStartDate

    private val _showErrorDialog = MutableLiveData<String>()
    val showErrorDialog: LiveData<String> = _showErrorDialog

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _reminderWithPets = MutableLiveData<ReminderWithPets>()
    val reminderWithPets: LiveData<ReminderWithPets> = _reminderWithPets

    enum class ActionReminder {
        UPDATE,
        CREATE,
    }

    private var action: ActionReminder = ActionReminder.CREATE

    init {
        enableForm.set(false)
    }

    fun initValues(reminderPetsJoinEntity: ReminderPetsJoinEntity?) {
        reminderPetsJoinEntity?.let {
            action = ActionReminder.UPDATE
            this.reminderPetsJoin = it
            this.reminderEntity = it.reminder
            listVaccines = it.reminder.vaccines.map { VaccineFieldEntity(it) }.toMutableList()
            enableForm.set(true)
            enableButton.set(true)
            validateVaccines()
        }
    }

    fun getSelectCategories() {
        val categories = CategoryReminderEntity.getCategories()
        if (action == ActionReminder.UPDATE) {
            reminderPetsJoin.reminder.categoryReminder.let { category ->
                categories.firstOrNull { category?.name == it.name }?.isSelected = true
            }
        }
        _categoriesReminder.postValue(categories)
    }

    fun selectAnimalEntity() {
        reminderPetsJoin.pets = listPets.value?.filter { it.isSelected } ?: listOf()
        enableForm()
    }

    fun setCategoryReminder() {
        reminderEntity.categoryReminder = categoriesReminder.value?.firstOrNull { it.isSelected }
        validateVaccines()
        enableForm()
    }

    private fun enableForm() {
        val atLeastPetIsSelected = reminderPetsJoin.pets.isNotEmpty()
        val atLeastCategoryIsSelected = reminderEntity.categoryReminder != null
        enableForm.set(atLeastPetIsSelected && atLeastCategoryIsSelected)
    }

    fun validateVaccines(): Boolean {
        enableVaccines.set(reminderEntity.categoryReminder is CategoryReminderEntity.VaccineReminder)
        return if (enableVaccines.get()) {
            listVaccines.firstOrNull { it.nameSelected.isNotEmpty() } != null
        } else {
            true
        }
    }

    fun getPets() {
        getPetsJob?.cancel()
        getPetsJob =
            getPetUseCase().onEach { pets ->
                if (pets.isEmpty()) setData()
                _listPets.postValue(
                    pets.map {
                        val petEntity = it.toPetEntity()
                        petEntity.isSelected =
                            reminderPetsJoin.pets.let {
                                it.firstOrNull { it.name == petEntity.name } != null
                            }
                        petEntity
                    },
                )
            }.launchIn(viewModelScope)
    }

    private fun setData() {
        viewModelScope.launch {
            val pets =
                listOf(
                    PetEntity(
                        null,
                        "https://www.telegraph.co.uk/content/dam/news/2023/06/10/TELEMMGLPICT000296384999_16864028803870_trans_NvBQzQNjv4BqrCS9JVgwgb8GODK1xmD4xlHwtdpQwyNje2OyIL7x97s.jpeg",
                        "Paul Pugba1",
                        "Perro",
                        "100.00",
                        Sex.MALE,
                        birthdate = "12/02/2010",
                        false,
                    ),
                    PetEntity(
                        null,
                        "https://static01.nyt.com/images/2024/01/16/multimedia/16xp-dog-01-lchw/16xp-dog-01-lchw-videoSixteenByNineJumbo1600.jpg",
                        "Paul",
                        "Perro",
                        "101.00",
                        Sex.MALE,
                        birthdate = "12/02/2010",
                        false,
                    ),
                    PetEntity(
                        null,
                        "https://cdn.britannica.com/79/232779-050-6B0411D7/German-Shepherd-dog-Alsatian.jpg",
                        "Paul Pugba3",
                        "Perro",
                        "102.00",
                        Sex.MALE,
                        birthdate = "12/02/2010",
                        false,
                    ),
                )
            pets.forEach {
                insertPetUseCase(
                    Pet(
                        image = it.image,
                        name = it.name,
                        specie = it.specie,
                        weight = it.weight.toDoubleOrNull() ?: 0.0,
                        sex = it.sex,
                        birthdate = it.birthdate,
                    ),
                )
            }
        }
    }

    fun getOptionsRepeat() {
        _listOptionsRepeat.postValue(
            getTemporalRepeat()
        )
    }

    fun getAlarmOptions() {
        _listAlarms.postValue(
            getTemporalAlarmOptions()
        )
    }

    fun getOptionsDurationRepeat() {
        _listDurationRepeat.postValue(
            getTemporalDuration()
        )
    }

    fun getOptionStartDate() {
        _optionStartDate.postValue(
            getTemporalDate()
        )
    }

    fun getOptionStartHour() {
        _optionStartHour.postValue(
            getTemporalHour()
        )
    }

    fun getStartHourSelected(): String? {
        return (_optionStartHour.value?.firstOrNull() as ScheduleOption?)?.hour?.let {
            reminderEntity.startHour = it
            it
        }
    }

    fun getStartDateSelected(): String? {
        (_optionStartDate.value?.firstOrNull() as CalendarSimple?)?.date?.let {
            reminderEntity.startDate = CalendarUtils.getFormatDate4(it)
            return CalendarUtils.getFormatDate(it)
        }
        reminderEntity.startDate = CalendarUtils.getFormatDate4(Calendar.getInstance().time)
        return CalendarUtils.getFormatDate(Calendar.getInstance().time)
    }

    fun getOptionRepeat(): String? {
        val optionSelected = _listOptionsRepeat.value?.firstOrNull { it.isSelected }
        when (optionSelected) {
            is TextOption -> {
                reminderEntity.repeatOption = optionSelected.value
                reminderEntity.countRepeatOption = null
                return optionSelected.name
            }

            is CounterOption -> {
                optionSelected.category?.let {
                    reminderEntity.repeatOption = optionSelected.category
                    reminderEntity.countRepeatOption = optionSelected.counter
                    return optionSelected.name + " " + optionSelected.counter
                }
            }
        }
        return null
    }

    fun getDurationRepeat(): String? {
        return _listDurationRepeat.value?.firstOrNull { it.isSelected }?.let { option ->
            when (option) {
                is TextOption -> {
                    reminderEntity.durationTypeRepeat = TypeOption.TEXT
                    reminderEntity.durationRepeat = option.name
                    option.name
                }

                is CalendarOptionNormal -> {
                    option.date?.let {
                        reminderEntity.durationTypeRepeat = TypeOption.DATE
                        reminderEntity.durationRepeat = CalendarUtils.getFormatDate2(it)
                        "hasta el " + CalendarUtils.getFormatDate2(it)
                    }
                }

                is CounterOption -> {
                    reminderEntity.durationTypeRepeat = TypeOption.COUNTER
                    reminderEntity.durationRepeat = option.counter.toString()
                    option.counter.toString() + " veces"
                }

                else -> null
            }
        }
    }

    fun getAlarms(): String? {
        val options = _listAlarms.value
        if (options.isNullOrEmpty()) return null
        var tempString = ""
        options.forEach {
            val temp = (it as CounterOption)
            when (temp.category) {
                ValueTextOption.MINUTES -> {
                    reminderEntity.alarmInMinutes = temp.counter
                    tempString += reminderEntity.alarmInMinutes.toString() + " minutos "
                }

                ValueTextOption.DAYS -> {
                    reminderEntity.alarmInDays = temp.counter
                    tempString += reminderEntity.alarmInDays.toString() + " dias "
                }

                ValueTextOption.HOUR -> {
                    reminderEntity.alarmInHours = temp.counter
                    tempString += reminderEntity.alarmInHours.toString() + " horas "
                }

                else -> {}
            }
        }
        return tempString
    }

    fun setNameReminder(name: String) {
        reminderEntity.title = name
    }

    fun setDescriptionReminder(description: String) {
        reminderEntity.description = description
    }

    fun addImages(images: List<Uri>) {
        reminderEntity.listImages = images.map { it.toString() }
    }

    fun validateForm(): String {
        if (reminderEntity.title.isEmpty()) {
            enableButton.set(false)
            return "Llena el nombre"
        }
        if (reminderEntity.categoryReminder == null) {
            enableButton.set(false)
            return "Selecciona una categoria"
        }
        if (reminderEntity.startDate.isEmpty()) {
            return "Selecciona fecha de inicio"
        }
        if (!validateVaccines()) {
            enableButton.set(false)
            return "Selecciona vacunas"
        }
        enableButton.set(true)
        return ""
    }

    fun createReminder() {
        val error = validateForm()
        if (error.isNotEmpty()) {
            _showErrorDialog.postValue(error)
            return
        }
        viewModelScope.launch {
            try {
                _loading.postValue(true)
                if (action == ActionReminder.UPDATE) {
                    reminderPetsJoin.reminder.vaccines =
                        listVaccines.mapNotNull {
                            it.nameSelected.ifEmpty { null }
                        }
                    updateReminderUseCase(reminderPetsJoin.reminder.toReminder())
                    reminderPetsJoin.reminder.reminderId?.let { deleteReminderWithPets(it) }
                    reminderPetsJoin.pets.forEach {
                        insertReminderWithPetsUseCase(
                            ReminderPetJoin(
                                reminderPetsJoin.reminder.reminderId ?: 0, it.petId ?: 0
                            )
                        )
                    }
                    _reminderWithPets.postValue(
                        ReminderWithPets(
                            reminderPetsJoin.reminder.toReminder(),
                            reminderPetsJoin.pets.map { it.toPet() },
                        ),
                    )
                    _loading.postValue(false)
                } else {
                    reminderEntity.vaccines =
                        listVaccines.mapNotNull {
                            it.nameSelected.ifEmpty { null }
                        }
                    val reminderId = insertReminderUseCase(reminderEntity.toReminder())
                    reminderEntity.reminderId = reminderId
                    reminderPetsJoin.reminder = reminderEntity
                    reminderPetsJoin.pets.forEach {
                        insertReminderWithPetsUseCase(ReminderPetJoin(reminderId, it.petId ?: 0))
                    }
                    _reminderWithPets.postValue(
                        ReminderWithPets(
                            reminderPetsJoin.reminder.toReminder(),
                            reminderPetsJoin.pets.map { it.toPet() },
                        ),
                    )
                    _loading.postValue(false)
                }
            } catch (e: Exception) {
                _showErrorDialog.postValue(e.localizedMessage)
            }
        }
    }

    private fun getTemporalDate(): List<CalendarSimple> {
        return listOf(
            CalendarSimple("Seleccionar Horario Inicio").apply {
                this.date = CalendarUtils.stringToDate(reminderEntity.startDate, CONST_FORMAT)
            }
        )
    }


    private fun getTemporalHour(): List<ScheduleOption> {
        return listOf(
            ScheduleOption("Seleccionar Horario de Inicio", reminderEntity.startHour)
        )
    }

    private fun getTemporalRepeat(): List<OptionViewInterface> {
        val options = listOf(
            TextOption("No Repetir", ValueTextOption.DONT_REPEAT),
            CounterOption("Cada dia", category = ValueTextOption.ALL_DAYS),
            CounterOption("Cada semana", category = ValueTextOption.ALL_WEEKS),
            CounterOption("Cada mes", category = ValueTextOption.ALL_MONTHS),
            CounterOption("Cada año", category = ValueTextOption.ALL_YEARS),
        )
        options.forEach {
            if (it is TextOption && it.value.name == reminderEntity.repeatOption.name) {
                it.isSelected = true
            }
            if (it is CounterOption && it.category?.name == reminderEntity.repeatOption.name) {
                it.isSelected = true
                it.counter = reminderEntity.countRepeatOption ?: 0
            }
        }
        return options
    }

    private fun getTemporalAlarmOptions(): List<OptionViewInterface> {
        return listOf(
            CounterOption(
                "minutos",
                isSelected = true,
                category = ValueTextOption.MINUTES,
                counter = reminderEntity.alarmInMinutes
            ),
            CounterOption(
                "horas",
                isSelected = true,
                category = ValueTextOption.HOUR,
                counter = reminderEntity.alarmInHours
            ),
            CounterOption(
                "dias",
                isSelected = true,
                category = ValueTextOption.DAYS,
                counter = reminderEntity.alarmInDays
            ),
        )
    }

    private fun getTemporalDuration(): List<OptionViewInterface> {
        val options = listOf(
            TextOption("Para siempre", ValueTextOption.FOR_EVER),
            CounterOption("Numero de veces"),
            CalendarOptionNormal("Hasta el dia"),
        )
        options.forEach {
            if (it is TextOption && it.name == reminderEntity.durationRepeat) {
                it.isSelected = true
            }
            if (it is CounterOption && reminderEntity.durationTypeRepeat == TypeOption.COUNTER) {
                it.isSelected = true
                it.counter = reminderEntity.durationRepeat?.toInt() ?: 0
            }
            if (it is CalendarOptionNormal && reminderEntity.durationTypeRepeat == TypeOption.DATE) {
                it.isSelected = true
                it.date = CalendarUtils.stringToDate(
                    reminderEntity.durationRepeat ?: "",
                    "dd 'de' MMM 'de' yyyy"
                )
            }
        }
        return options
    }
}
