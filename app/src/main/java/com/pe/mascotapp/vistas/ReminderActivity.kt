package com.pe.mascotapp.vistas

import android.Manifest
import android.content.Intent
import android.content.Intent.EXTRA_ALLOW_MULTIPLE
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.databinding.ActivityReminderBinding
import com.pe.mascotapp.notifications.AlarmEventHelper
import com.pe.mascotapp.utils.CalendarUtils
import com.pe.mascotapp.viewmodels.ReminderViewModel
import com.pe.mascotapp.vistas.adapters.CategoryReminderAdapter
import com.pe.mascotapp.vistas.adapters.ImageGalleryAdapter
import com.pe.mascotapp.vistas.adapters.OptionViewInterface
import com.pe.mascotapp.vistas.adapters.PetAdapter
import com.pe.mascotapp.vistas.adapters.ReminderPetsJoinEntity
import com.pe.mascotapp.vistas.adapters.TypeOption
import com.pe.mascotapp.vistas.adapters.VaccineFieldAdapter
import com.pe.mascotapp.vistas.adapters.mapValueTextOption
import com.pe.mascotapp.vistas.dialogs.DialogOption
import com.pe.mascotapp.vistas.entities.VaccineFieldEntity
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class ReminderActivity : AppCompatActivity() {
    companion object {
        const val MY_CHANNEL_ID = "myChannel"
        const val MY_CHANNEL_NAME = "MySuperChannel"
        const val TYPE_MEDIA = "image/*"
        const val GRID_CATEGORIES = 5
        const val GRID_IMAGES = 3
        const val REQUEST_CODE_PERMISSION = 10001
    }

    private val alarmEventHelper by lazy { setUpAlarmHelper() }

    private lateinit var binding: ActivityReminderBinding

    private val viewModel: ReminderViewModel by viewModels()

    private val imageGalleryAdapter = ImageGalleryAdapter(ArrayList()){
        images.removeAt(it)
    }

    private val pickImageFromGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) result.data?.let { addImages(it) }
        }

    private var permissionMediaLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            handlePermissionGallery(true in permissions.values)
        }

    private fun setUpAlarmHelper(): AlarmEventHelper {
        return AlarmEventHelper(applicationContext)
    }
    val images = mutableListOf<Uri>()
    private fun addImages(dataImages: Intent) {
        val data: Intent = dataImages
        data.clipData?.itemCount?.let { itemCount ->
            for (i in 0 until itemCount) {
                data.clipData?.getItemAt(i)?.let { images.add(it.uri) }
            }
        }
        imageGalleryAdapter.images = images.toCollection(ArrayList())
        imageGalleryAdapter.notifyDataSetChanged()
        viewModel.addImages(images)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
            }
        }
    }

    private fun verifyPermissionGallery() {
        val permissions =
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO)
            } else {
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        permissionMediaLauncher.launch(permissions)
    }

    private fun setUpRecyclerViews() {
        binding.rvAnimals.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, GRID_CATEGORIES)
        }
        binding.rvImages.apply {
            layoutManager = GridLayoutManager(context, GRID_IMAGES)
            adapter = imageGalleryAdapter
        }
        val vaccineAdapter = VaccineFieldAdapter(viewModel.listVaccines)
        vaccineAdapter.addVaccineField = {
            viewModel.listVaccines.add(VaccineFieldEntity())
            vaccineAdapter.notifyItemInserted(viewModel.listVaccines.size)
            viewModel.validateForm()
        }
        vaccineAdapter.removeVaccine = {
            viewModel.listVaccines.removeAt(it)
            vaccineAdapter.notifyItemRemoved(it)
            viewModel.validateForm()
        }
        vaccineAdapter.validateSelected = {
            viewModel.validateForm()
        }
        binding.rvVaccineFields.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = vaccineAdapter
        }
        binding.edtDescription.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                    viewModel.setDescriptionReminder(s.toString())
                }
            },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReminderBinding.inflate(layoutInflater)
        binding.reminderViewModel = viewModel
        val reminderPetsJoinEntity = intent.getParcelableExtra<ReminderPetsJoinEntity>("BUNDLE_REMINDER")
        reminderPetsJoinEntity?.let {
            binding.nameReminder.setText(it.reminder.title)
            binding.edtDescription.setText(it.reminder.description)
            binding.tvDateStart.text = CalendarUtils.stringToDate(it.reminder.startDate,CalendarUtils.CONST_FORMAT)
                    ?.let { it1 -> CalendarUtils.getFormatDate(it1) }
            binding.tvHourStart.text = it.reminder.startHour
            binding.tvRepeat.text = "${it.reminder.repeatOption.mapValueTextOption()}  ${it.reminder?.countRepeatOption ?: ""}"
            binding.tvAddDuration.text =
                when (it.reminder.durationTypeRepeat) {
                    TypeOption.COUNTER -> " ${it.reminder.durationRepeat} veces"
                    TypeOption.TEXT -> "Para siempre"
                    TypeOption.DATE -> it.reminder.durationRepeat
                    null -> "+ Anadir Duracion"
                }
            binding.tvAlarm.text ="${it.reminder.alarmInMinutes} minutos ${it.reminder.alarmInHours} horas ${it.reminder.alarmInDays} dias"
            viewModel.initValues(it)
        }
        viewModel.getSelectCategories()
        viewModel.getPets()
        checkPermission()

        alarmEventHelper.createChannel()
        setUpObservables()
        setUpRecyclerViews()
        setUpListeners()

        setContentView(binding.root)
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            requestNotificationPermission()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQUEST_CODE_PERMISSION)
        }
    }

    private fun setUpListeners() {
        binding.nameReminder.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                    viewModel.setNameReminder(s.toString())
                    viewModel.validateForm()
                }
            },
        )

        binding.llAddImage.setOnClickListener {
            verifyPermissionGallery()
        }
        binding.llAlarm.setOnClickListener {
            viewModel.getAlarmOptions()
        }
        binding.tvRepeat.setOnClickListener {
            viewModel.getOptionsRepeat()
        }
        binding.tvAddDuration.setOnClickListener {
            viewModel.getOptionsDurationRepeat()
        }
        binding.tvDateStart.setOnClickListener {
            viewModel.getOptionStartDate()
        }
        binding.tvHourStart.setOnClickListener {
            viewModel.getOptionStartHour()
        }
        binding.buttonAccept.setOnClickListener {
            viewModel.createReminder()
        }
    }

    private fun handlePermissionGallery(isGranted: Boolean) {
        if (isGranted) {
            val intent =
                Intent(Intent.ACTION_GET_CONTENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = TYPE_MEDIA
                    putExtra(EXTRA_ALLOW_MULTIPLE, true)
                }
            pickImageFromGalleryLauncher.launch(intent)
        }
    }

    private fun setUpObservables() {
        viewModel.listPets.observe(this) {
            binding.rvAnimals.adapter =
                PetAdapter(it) {
                    viewModel.selectAnimalEntity()
                    viewModel.validateForm()
                    binding.txtCounter.text = "(${it.filter { it.isSelected }.size})"
                }
        }
        viewModel.categoriesReminder.observe(this) {
            binding.rvCategories.adapter =
                CategoryReminderAdapter(it) {
                    viewModel.setCategoryReminder()
                    viewModel.validateForm()
                }
        }
        viewModel.listOptionsRepeat.observe(this) { options ->
            showDialogOptions(options) {
                viewModel.getOptionRepeat()?.let { binding.tvRepeat.text = it }
                viewModel.validateForm()
            }
        }
        viewModel.listDurationRepeat.observe(this) { options ->
            showDialogOptions(options) {
                viewModel.getDurationRepeat()?.let { binding.tvAddDuration.text = it }
                viewModel.validateForm()
            }
        }
        viewModel.listAlarms.observe(this) { options ->
            showDialogOptions(options, true) {
                viewModel.getAlarms()?.let {
                    binding.tvAlarm.text = it
                }
                viewModel.validateForm()
            }
        }
        viewModel.optionStartHour.observe(this) { options ->
            showDialogOptions(options) {
                viewModel.getStartHourSelected()?.let {
                    binding.tvHourStart.text = it
                    viewModel.validateForm()
                }
            }
        }
        viewModel.optionStartDate.observe(this) { options ->
            showDialogOptions(options) {
                viewModel.getStartDateSelected()?.let {
                    binding.tvDateStart.text = it
                    viewModel.validateForm()
                }
            }
        }

        viewModel.showErrorDialog.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(this) {
            if (!it) {
                setResult(RESULT_OK)
                finish()
            }
        }

        viewModel.reminderWithPets.observe(this) {
            alarmEventHelper.setAlarmPeriod(listOf(it))
        }
    }

    private fun showDialogOptions(
        listOptions: List<OptionViewInterface>,
        availableMultipleSelect: Boolean = false,
        callBackOptions: () -> Unit,
    ) {
        val dialogOptions = DialogOption()
        dialogOptions.setListOptions(listOptions)
        dialogOptions.setCallBackOptions { callBackOptions() }
        dialogOptions.setAvailableMultipleSelect(availableMultipleSelect)
        dialogOptions.show(supportFragmentManager, DialogOption::class.java.simpleName)
    }
}
